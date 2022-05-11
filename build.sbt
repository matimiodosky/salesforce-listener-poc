ThisBuild / scalaVersion := "2.13.8"

ThisBuild / version := "1.0-SNAPSHOT"

val AkkaVersion = "2.6.18"

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .settings(
    name := """salesforce-listener-poc""",
    libraryDependencies ++= Seq(
      guice,
      "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % Test,
      "com.github.forcedotcom" % "EMP-Connector" % "-SNAPSHOT",
      "com.typesafe.akka" %% "akka-stream-kafka" % "3.0.0",
      "com.typesafe.akka" %% "akka-stream" % AkkaVersion
    )

)

resolvers += "jitpack" at "https://jitpack.io"
