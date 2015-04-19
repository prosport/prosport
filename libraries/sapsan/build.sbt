organization := "ru.myscala"

name := "sapsan"

version := "0.2"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  "org.avaje.ebeanorm" % "avaje-ebeanorm-api" % "3.1.1",
  cache
)     


publishArtifact in (Compile, packageDoc) := false

publishArtifact in (Compile, packageSrc) := false

play.Project.playJavaSettings
