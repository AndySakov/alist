name := "alist"
organization := "com.shiftio"
version := "1.0-SNAPSHOT"
scalaVersion := "2.13.1"
libraryDependencies ++= Seq(
  "com.github.pathikrit" %% "better-files" % "3.9.1",
  "com.colofabrix.scala" %% "figlet4s-core" % "0.3.0",
  "org.scalatest" %% "scalatest" % "3.1.0" % Test,
  "com.github.nscala-time" %% "nscala-time" % "2.28.0",
  "com.lihaoyi" %% "upickle" % "1.3.8",
  "com.lihaoyi" %% "fansi" % "0.2.14"
)
