package com.github.agriinsight.edge

import akka.stream.alpakka.mqtt.MqttConnectionSettings
import com.typesafe.config.Config
import java.util.concurrent.TimeUnit
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence
import scala.concurrent.duration.Duration
import scala.concurrent.duration.FiniteDuration
import scala.jdk.DurationConverters.JavaDurationOps

case class MqttConfig(connectionSettings: MqttConnectionSettings, delay: FiniteDuration)

object MqttConfig:

  def fromConfig(config: Config): MqttConfig =
    val mqtt = config.getConfig("mqtt")
    val connection = mqtt.getConfig("connection")
    val publish = mqtt.getConfig("publish")
    MqttConfig(
      MqttConnectionSettings(
        connection.getString("broker"),
        connection.getString("client-id"),
        new MemoryPersistence
      ),
      FiniteDuration(publish.getDuration("delay").toMillis, TimeUnit.MILLISECONDS)
    )
