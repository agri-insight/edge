package com.github.agriinsight.edge.publish

import com.github.agriinsight.edge.data.Temperature
import org.apache.pekko.actor.typed.ActorSystem
import org.apache.pekko.event.slf4j.SLF4JLogging
import org.apache.pekko.stream.connectors.mqtt.scaladsl.MqttSink
import org.apache.pekko.stream.connectors.mqtt.MqttConnectionSettings
import org.apache.pekko.stream.connectors.mqtt.MqttMessage
import org.apache.pekko.stream.connectors.mqtt.MqttQoS
import org.apache.pekko.stream.scaladsl.Source
import org.apache.pekko.util.ByteString
import org.apache.pekko.Done
import org.json4s._
import org.json4s.jackson.Serialization.write
import scala.concurrent.duration.DurationDouble
import scala.concurrent.Future
import scala.util.Random

object MqttTemperaturePublisher extends SLF4JLogging {

  def apply(
      connectionSettings: MqttConnectionSettings
  )(topic: String)(implicit
      actorSystem: ActorSystem[Nothing]
  ): Future[Done] = {
    log.debug(s"Start publish to topic $topic")
    Source
      .repeat(Source.lazySingle(() => Temperature.random(-20.0, 40.0)))
      .flatMapConcat(_.delay {
        (Random.nextDouble() match {
          case value if value <= 0.8  => Random.between(5_000.0, 100_000.0)
          case value if value <= 0.99 => Random.between(100_000.0, 1_000_000.0)
          case _                      => Random.between(1_000_000.0, 3_600_000.0)
        }).millis
      })
      .map { temperature =>
        val payload = write(temperature)(DefaultFormats)
        val message = MqttMessage(topic, ByteString.fromString(payload))
          .withQos(MqttQoS.AtLeastOnce)
          .withRetained(true)
        log.debug(s"publish $payload to topic $topic")
        message
      }
  }.runWith(MqttSink(connectionSettings, MqttQoS.AtLeastOnce))
}
