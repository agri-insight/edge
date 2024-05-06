import sbt.*

object Dependencies {

  object ScalaTest {

    val Scalactic = "org.scalactic" %% "scalactic" % "3.2.18"
    val ScalaTest = "org.scalatest" %% "scalatest" % "3.2.18" % Test
    val All: Seq[ModuleID] = Seq(Scalactic, ScalaTest)
  }

  object Akka {

    val Version = "2.9.0"

    val Mqtt = "com.lightbend.akka" %% "akka-stream-alpakka-mqtt" % "7.0.2"
    val TypedActor = "com.typesafe.akka" %% "akka-actor-typed" % Version
    val Stream = "com.typesafe.akka" %% "akka-stream" % Version
    val Slf4j = "com.typesafe.akka" %% "akka-slf4j" % Version

    val All: Seq[ModuleID] = Seq(Mqtt, Stream, TypedActor, Slf4j)
  }

  object Logging {
    val Slf4j = "ch.qos.logback" % "logback-classic" % "1.5.6"
    val All: Seq[ModuleID] = Seq(Slf4j)
  }

  object Json {

    val Json4S = "org.json4s" %% "json4s-jackson" % "4.0.7"
    val All: Seq[ModuleID] = Seq(Json4S)
  }
}
