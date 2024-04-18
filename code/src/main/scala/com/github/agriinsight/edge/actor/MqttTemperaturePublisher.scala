package com.github.agriinsight.edge.actor

import akka.actor.typed.ActorSystem
import akka.event.slf4j.SLF4JLogging
import akka.stream.alpakka.mqtt.scaladsl.MqttSink
import akka.stream.alpakka.mqtt.MqttConnectionSettings
import akka.stream.alpakka.mqtt.MqttMessage
import akka.stream.alpakka.mqtt.MqttQoS
import akka.stream.scaladsl.Source
import akka.util.ByteString
import akka.Done
import com.github.agriinsight.edge.data.Temperature
import org.json4s._
import org.json4s.jackson.Serialization.write
import scala.concurrent.duration._
import scala.concurrent.Future

object MqttTemperaturePublisher extends SLF4JLogging {

  def apply(
      connectionSettings: MqttConnectionSettings
  )(topic: String)(implicit
      actorSystem: ActorSystem[Nothing],
      delay: FiniteDuration
  ): Future[Done] = {
    log.debug(s"Start publish to topic $topic")
    Source
      .repeat(Source.lazySingle(() => Temperature.random))
      .flatMapConcat(identity)
      .delay(delay)
      .map { temperature =>
        MqttMessage(topic, ByteString.fromString(write(temperature)(DefaultFormats)))
          .withQos(MqttQoS.AtLeastOnce)
          .withRetained(true)
      }
  }.runWith(MqttSink(connectionSettings, MqttQoS.AtLeastOnce))
}
