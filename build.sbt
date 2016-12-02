import org.scalastyle.sbt.ScalastylePlugin

scalaVersion := "2.11.8"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

libraryDependencies ++= {
  val akkaV          = "2.4.14"
  val akkaHttpV      = "10.0.0"
  val scalaTestV     = "2.2.6"
  val specs2V        = "3.8.5"
  val json4sV        = "3.4.1"
  val awsV           = "1.11.60"
  Seq(
    "com.typesafe.akka"  %% "akka-actor"             % akkaV,
    "com.typesafe.akka"  %% "akka-stream"            % akkaV,
    "com.typesafe.akka"  %% "akka-http"              % akkaHttpV,
    "com.typesafe.akka"  %% "akka-http-testkit"      % akkaHttpV,
    "de.heikoseeberger"  %% "akka-http-json4s"       % "1.11.0",
    "org.json4s"         %% "json4s-jackson"         % json4sV,
    "org.json4s"         %% "json4s-ext"             % json4sV,
    "joda-time"          %  "joda-time"              % "2.9.4",
    "org.joda"           %  "joda-convert"           % "1.8",
    "com.amazonaws"      %  "aws-java-sdk-s3"        % awsV,
    "com.typesafe.akka"  %% "akka-http-testkit"      % akkaHttpV         % "test",
    "com.typesafe.akka"  %% "akka-stream-testkit"    % akkaV             % "test",
    "org.specs2"         %% "specs2-core"            % specs2V           % "test",
    "org.specs2"         %% "specs2-mock"            % specs2V           % "test",
    "org.specs2"         %% "specs2-matcher-extra"   % specs2V           % "test",
    "org.scalatest"      %% "scalatest"              % scalaTestV        % "test"
  )
}

val assemblySettings = Seq(
  test in assembly := {},
  assemblyJarName in assembly := s"${name.value}.jar"
)

Revolver.settings

val `test-all` = taskKey[Unit]("Run Unit tests, integration tests and scalastyle.")

lazy val root = (project in file("."))
  .settings(
    organization := "co.delprks",
    name := "streams",
    javaOptions in run += "-Dconfig.resource=application.dev.conf",
    fork in run := true
  )
  .settings(
    `test-all` in Compile := Def.sequential(
      test in Test,
      (ScalastylePlugin.scalastyle in Compile).toTask("")
    ).value
  )
  .settings(assemblySettings)
