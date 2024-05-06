import sbt.*

object Dependencies {

  object ScalaTest {

    val Scalactic = "org.scalactic" %% "scalactic" % "3.2.18"
    val ScalaTest = "org.scalatest" %% "scalatest" % "3.2.18" % Test
    val All: Seq[ModuleID] = Seq(Scalactic, ScalaTest)
  }

  object Akka {

    val AkkaVersion = "2.9.0"
    val AlpakkaMqtt = "com.lightbend.akka" %% "akka-stream-alpakka-mqtt" % "7.0.2"
    val AkkaTypedActor = "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion
    val AkkaStream = "com.typesafe.akka" %% "akka-stream" % AkkaVersion
    val AkkaSlf4j = "com.typesafe.akka" %% "akka-slf4j" % AkkaVersion
    val Slf4j = "ch.qos.logback" % "logback-classic" % "1.5.6"

    val All: Seq[ModuleID] = Seq(AlpakkaMqtt, AkkaStream, AkkaTypedActor, AkkaSlf4j, Slf4j)
  }

  object Json {

    val Json4S = "org.json4s" %% "json4s-jackson" % "4.0.7"
    val All: Seq[ModuleID] = Seq(Json4S)
  }
}
