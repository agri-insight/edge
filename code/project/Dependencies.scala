import sbt.*

object Dependencies {

  object ScalaTest {

    val Scalastic = "org.scalactic" %% "scalactic" % "3.2.18"
    val ScalaTest = "org.scalatest" %% "scalatest" % "3.2.18" % Test
    val All: Seq[ModuleID] = Seq(Scalastic, ScalaTest)
  }

  object Akka {

    val AkkaVersion = "2.8.5"
    val AlpakkaMqtt = "com.lightbend.akka" %% "akka-stream-alpakka-mqtt" % "7.0.2"
    val AlpakkaCsv = "com.lightbend.akka" %% "akka-stream-alpakka-csv" % "7.0.2"
    val AkkaTypedActor = "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion
    val AkkaStream = "com.typesafe.akka" %% "akka-stream" % AkkaVersion

    val All: Seq[ModuleID] = Seq(AlpakkaMqtt, AlpakkaCsv, AkkaStream, AkkaTypedActor)
  }

  object Config {

    val PureConfig = "com.github.pureconfig" %% "pureconfig-core" % "0.17.6"
    val All: Seq[ModuleID] = Seq(PureConfig)
  }

  object Json {

    val Json4S = "org.json4s" %% "json4s-jackson" % "4.0.7"
    val All: Seq[ModuleID] = Seq(Json4S)
  }
}
