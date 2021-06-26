name := "purefunc"
version := "0.1"
scalaVersion := "2.13.6"

lazy val common = (project in file("."))
  .enablePlugins(ScalafmtPlugin)
  .enablePlugins(AssemblyPlugin)
  .settings(
    name := "purefunc",
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest"          % "3.2.0" % Test,
      "org.scalatest" %% "scalatest-core"     % "3.2.0",
      "org.scalatest" %% "scalatest-funsuite" % "3.2.0" % Test
    ),
    scalacOptions ++= Seq(
      "-feature",
      "-language:implicitConversions",
      "-language:higherKinds",
      "-language:postfixOps"
    ),
    Compile / run / mainClass := Some("company.ryzhkov.Program"),
    assembly / mainClass := Some("company.ryzhkov.Program"),
    assembly / assemblyJarName := "purefunc.jar"
  )

addCompilerPlugin("org.typelevel" % "kind-projector" % "0.13.0" cross CrossVersion.full)
