name := "prosport"

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  "org.avaje.ebeanorm" % "avaje-ebeanorm-api" % "3.1.1",
  "com.typesafe.play.plugins" %% "play-plugins-mailer" % "2.3.1",
  "postgresql" % "postgresql" % "9.1-901-1.jdbc4"
//  "ru.myscala" % "sapsan_2.10" % "0.2"
)

//resolvers += Resolver.url("SapsanAdmin GitHub Repository", url("http://rumataestorsky.github.io/releases/"))(Resolver.ivyStylePatterns)

//play.Project.playJavaSettings

lazy val sapsanAdmin = (project in file("libraries/sapsan")).enablePlugins(PlayScala)

lazy val main = project.in(file("."))
  .enablePlugins(PlayJava)
  .dependsOn(sapsanAdmin)
  .aggregate(sapsanAdmin)

//javacOptions ++= Seq("-source", "1.8", "-target", "1.8")

