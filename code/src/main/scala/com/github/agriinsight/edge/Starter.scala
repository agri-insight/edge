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
    implicit val delay: FiniteDuration = mqttConfig.delay

    implicit val system: ActorSystem[Nothing] = ActorSystem[Nothing](Behaviors.empty, "edge", config)
    MqttTemperaturePublisher(mqttConfig.connectionSettings)("temperature")
  }
}
