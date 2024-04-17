package com.github.agriinsight.edge.actor

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorSystem, Behavior}
import akka.stream.alpakka.csv.scaladsl.CsvParsing
import akka.stream.alpakka.csv.scaladsl.CsvToMap
import akka.stream.alpakka.mqtt.scaladsl.MqttSink
import akka.stream.alpakka.mqtt.MqttConnectionSettings
import akka.stream.alpakka.mqtt.MqttMessage
import akka.stream.alpakka.mqtt.MqttQoS
import akka.stream.scaladsl.FileIO
import akka.stream.scaladsl.Source
import akka.util.ByteString
import com.github.agriinsight.edge.Parser

import java.nio.file.Path
import org.json4s.*
import org.json4s.jackson.Serialization.write

import scala.concurrent.duration.*
import scala.concurrent.ExecutionContext

object MqttPublisher:

  def apply(
      connectionSettings: MqttConnectionSettings
  )(transform: Parser)(topic: String, resource: String)(using
      actorSystem: ActorSystem[Nothing],
      delay: FiniteDuration
  ): Behavior[MqttPublisher.Command] =
    Behaviors.receive { (context, message) =>
      message match
        case Publish =>
          context.log.debug(s"Start publish to topic $topic from $resource")
          Source
            .repeat(FileIO.fromPath(resourcePath(resource)))
            .flatMapConcat(identity)
            .via(CsvParsing.lineScanner())
            .via(CsvToMap.toMapAsStrings())
            .delay(delay)
            .flatMapConcat(map =>
              transform(map) match
                case Some(value) =>
                  val payload = ByteString.fromString(write(value)(DefaultFormats))
                  Source.single(
                    MqttMessage(topic, payload)
                      .withQos(MqttQoS.AtLeastOnce)
                      .withRetained(true)
                  )
                case None =>
                  context.log.error(s"Invalid message: $map")
                  Source.empty
            )
            .runWith(MqttSink(connectionSettings, MqttQoS.AtLeastOnce))
            .map(_ => context.log.debug(s"Send message to topic $topic from $resource"))(ExecutionContext.global)
      Behaviors.same
    }

  private def resourcePath(resource: String): Path =
    Path.of(Thread.currentThread().getContextClassLoader.getResource(resource).toURI)

  sealed trait Command

  case object Publish extends Command
