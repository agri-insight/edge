ThisBuild / scalaVersion := "3.3.0"

lazy val root = project
  .in(file("."))
  .settings(
    commonSettings,
    name := "edge",
    maintainer := "kivanval@gmail.com",
    version := "0.1.0-SNAPSHOT",
    Universal / packageName := name.value,
    libraryDependencies ++=
      Dependencies.ScalaTest.All ++
        Dependencies.Akka.All ++
        Dependencies.Json.All ++
        Dependencies.Config.All
  ).enablePlugins(UniversalPlugin, JavaAppPackaging)

val commonSettings = Seq(
  resolvers += "Akka library repository".at("https://repo.akka.io/maven")
)
