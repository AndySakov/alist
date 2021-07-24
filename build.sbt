name := "alist"
organization := "com.shiftio"
version := "1.0-SNAPSHOT"
scalaVersion := "2.13.1"
lazy val circeVersion = "0.12.3"
libraryDependencies ++= Seq(
  "com.github.pathikrit" %% "better-files" % "3.9.1",
  "com.colofabrix.scala" %% "figlet4s-core" % "0.3.0",
  "org.scalatest" %% "scalatest" % "3.1.0" % Test,
  "com.lihaoyi" %% "fansi" % "0.2.14",
  "com.github.tototoshi" %% "scala-csv" % "1.3.8"
)

lazy val main = "com.shiftio.alist.Main"
mainClass in(Compile, run) := Some(main)
mainClass in(Compile, packageBin) := Some(main)