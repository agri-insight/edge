package com.github.agriinsight.edge

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.ActorSystem
import com.github.agriinsight.edge.publish.MqttTemperaturePublisher
import com.typesafe.config.ConfigFactory
import scala.concurrent.duration.FiniteDuration

object Starter {

  def main(args: Array[String]): Unit = {
    val config = ConfigFactory.load()
    val mqttConfig = MqttConfig.fromConfig(config)

    implicit val system: ActorSystem[Nothing] = ActorSystem[Nothing](Behaviors.empty, "edge", config)

    for {
      value <- 1 to 1000
    } {
      MqttTemperaturePublisher(mqttConfig.connectionSettings)(s"soil/$value/temperature")
      MqttTemperaturePublisher(mqttConfig.connectionSettings)(s"air/$value/temperature")
    }
  }
}
