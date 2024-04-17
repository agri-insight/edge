import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import com.github.agriinsight.edge.MqttConfig
import com.github.agriinsight.edge.actor.MqttPublisher
import com.github.agriinsight.edge.data.Temperature
import com.typesafe.config.ConfigFactory

import scala.concurrent.duration.FiniteDuration

@main def start(): Unit =
  val config = ConfigFactory.load()
  val mqttConfig = MqttConfig.fromConfig(config)
  val rootBehavior = Behaviors.setup[Nothing] { implicit context =>
    given ActorSystem[Nothing] = context.system
    given FiniteDuration = mqttConfig.delay

    val temperaturePublisher =
      context.spawn(
        MqttPublisher(mqttConfig.connectionSettings)(Temperature.Parser)("temperature", "/data/temperature.csv"),
        "TemperaturePublisher"
      )

    Behaviors.empty
  }
  ActorSystem[Nothing](rootBehavior, "edge", config)

