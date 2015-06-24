organization := "ru.myscala"

name := "sapsan"

version := "0.2"

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  "org.avaje.ebeanorm" % "avaje-ebeanorm-api" % "3.1.1",
  "org.apache.commons" % "commons-io" % "1.3.2"
)     


publishArtifact in (Compile, packageDoc) := false

publishArtifact in (Compile, packageSrc) := false

//play.Project.playJavaSettings
