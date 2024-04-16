val scala3Version = "3.3.0"

lazy val root = project
  .in(file("."))
  .settings(
    commonSettings,
    name := "edge",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    libraryDependencies ++=
      Dependencies.ScalaTest.All ++
        Dependencies.Akka.All
  )

val commonSettings = Seq(
  resolvers += "Akka library repository".at("https://repo.akka.io/maven")
)
