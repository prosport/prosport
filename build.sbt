name := "prosport"

version := "1.0-SNAPSHOT"

ebeanEnabled := false

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  "org.avaje.ebeanorm" % "avaje-ebeanorm-api" % "3.1.1",
  "com.typesafe.play.plugins" %% "play-plugins-mailer" % "2.3.1",
  "postgresql" % "postgresql" % "9.1-901-1.jdbc4"
)

play.Project.playJavaSettings
