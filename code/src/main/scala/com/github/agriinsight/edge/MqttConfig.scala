package com.github.agriinsight.edge

import akka.stream.alpakka.mqtt.MqttConnectionSettings
import com.typesafe.config.Config
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence

case class MqttConfig(connectionSettings: MqttConnectionSettings)

object MqttConfig {

  def fromConfig(config: Config): MqttConfig = {

    val mqtt = config.getConfig("mqtt")
    val connection = mqtt.getConfig("connection")
    MqttConfig(
      MqttConnectionSettings(
        connection.getString("broker"),
        connection.getString("client-id"),
        new MemoryPersistence
      )
    )
  }
}
