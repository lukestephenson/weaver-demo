ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.2.2"

lazy val root = (project in file("."))
  .settings(
    name := "weaver-demo",
    libraryDependencies ++= Seq(
      "org.typelevel" %% "cats-effect" % "3.4.7",
      "org.apache.kafka" % "kafka-clients" % "2.8.2",
      "org.scalatest" %% "scalatest" % "3.2.15" % Test,
      "com.disneystreaming" %% "weaver-cats" % "0.8.1" % Test,
      "com.dimafeng" %% "testcontainers-scala-kafka" % "0.40.12" % Test,
    ),
    testFrameworks += new TestFramework("weaver.framework.CatsEffect"),
  )
