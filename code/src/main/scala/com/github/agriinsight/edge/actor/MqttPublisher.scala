package com.github.agriinsight.edge.actor

import akka.actor.ActorSystem
import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors
import akka.stream.alpakka.csv.scaladsl.{CsvParsing, CsvToMap}
import akka.stream.alpakka.mqtt.{MqttConnectionSettings, MqttMessage, MqttQoS}
import akka.stream.alpakka.mqtt.scaladsl.MqttSink
import akka.stream.scaladsl.{FileIO, Source}
import akka.util.ByteString
import com.github.agriinsight.edge.Parser
import org.json4s.*
import org.json4s.jackson.Serialization.write

import java.nio.file.Path
import scala.concurrent.ExecutionContext
import scala.concurrent.duration.*

object MqttPublisher:

  def apply[T <: AnyRef: Parser](connectionSettings: MqttConnectionSettings)(topic: String, resource: String)(using
      actorSystem: ActorSystem,
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
              summon[Parser[T]].apply(map) match
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
