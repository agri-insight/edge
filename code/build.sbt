ThisBuild / scalaVersion := "2.13.11"

lazy val root = project
  .in(file("."))
  .settings(
    commonSettings,
    name := "agent",
    maintainer := "kivanval@gmail.com",
    version := "0.1.0-SNAPSHOT",
    Universal / packageName := name.value,
    libraryDependencies ++=
      Dependencies.ScalaTest.All ++
        Dependencies.Akka.All ++
        Dependencies.Json.All
  ).enablePlugins(UniversalPlugin, JavaAppPackaging)

val commonSettings = Seq(
  resolvers += "Akka library repository".at("https://repo.akka.io/maven")
)
