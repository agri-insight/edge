lazy val root = project
  .in(file("."))
  .settings(
    commonSettings,
    name := "edge",
    maintainer := "kivanval@gmail.com",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    libraryDependencies ++=
      Dependencies.ScalaTest.All ++
        Dependencies.Akka.All ++
        Dependencies.Json.All ++
        Dependencies.Config.All
  ).enablePlugins(UniversalPlugin, JavaAppPackaging)
val scala3Version = "3.3.0"

val commonSettings = Seq(
  resolvers += "Akka library repository".at("https://repo.akka.io/maven")
)
