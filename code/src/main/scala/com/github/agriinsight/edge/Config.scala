package com.github.agriinsight.edge

import akka.stream.alpakka.mqtt.MqttConnectionSettings
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence
import pureconfig.error.ConfigReaderFailures
import pureconfig.generic.derivation.default.derived
import pureconfig.syntax.ConfigReaderOps
import pureconfig.ConfigReader
import pureconfig.ConfigSource

case class MqttConnectionConfig(broker: String, clientId: String) derives ConfigReader

extension (settings: MqttConnectionSettings.type)

  def fromConfig(): Either[ConfigReaderFailures, MqttConnectionSettings] =
    for
      conf <- ConfigSource.default.config()
      mqtt <- conf.getConfig("mqtt.connection").to[MqttConnectionConfig]
    yield MqttConnectionSettings(mqtt.broker, mqtt.clientId, new MemoryPersistence)
