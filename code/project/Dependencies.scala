import sbt.*

object Dependencies {
  object ScalaTest {
    val Scalastic          = "org.scalactic" %% "scalactic" % "3.2.18"
    val ScalaTest          = "org.scalatest" %% "scalatest" % "3.2.18" % Test
    val All: Seq[ModuleID] = Seq(Scalastic, ScalaTest)
  }
  object Akka {
    val AkkaVersion = "2.9.0"
    val AlpakkaMqtt = "com.lightbend.akka" %% "akka-stream-alpakka-mqtt" % "7.0.2"
    val AkkaStream = "com.typesafe.akka" %% "akka-stream" % AkkaVersion
    val All: Seq[ModuleID] = Seq(AlpakkaMqtt, AkkaStream)
  }
}
