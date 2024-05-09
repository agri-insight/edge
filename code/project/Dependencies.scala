import sbt.*

object Dependencies {

  object ScalaTest {

    val Scalactic = "org.scalactic" %% "scalactic" % "3.2.18"
    val ScalaTest = "org.scalatest" %% "scalatest" % "3.2.18" % Test
    val All: Seq[ModuleID] = Seq(Scalactic, ScalaTest)
  }

  object Pekko {

    val Version = "1.0.2"

    val Mqtt = "org.apache.pekko" %% "pekko-connectors-mqtt" % "1.0.2"
    val TypedActor = "org.apache.pekko" %% "pekko-actor-typed" % Version
    val Stream = "org.apache.pekko" %% "pekko-stream" % Version
    val Slf4j = "org.apache.pekko" %% "pekko-slf4j" % Version

    val All: Seq[ModuleID] = Seq(Mqtt, Stream, TypedActor, Slf4j)
  }

  object Logging {
    val Logback = "ch.qos.logback" % "logback-classic" % "1.5.6"
    val All: Seq[ModuleID] = Seq(Logback)
  }

  object Json {

    val Json4S = "org.json4s" %% "json4s-jackson" % "4.0.7"
    val All: Seq[ModuleID] = Seq(Json4S)
  }
}
