import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

    val appName         = "c-ration"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
        cache,
      "com.typesafe" % "config" % "1.0.2",
      "org.pegdown" % "pegdown" % "1.4.1",
      "org.mockito" % "mockito-all" % "1.9.5" % "test"
    )

    val main = play.Project(appName, appVersion, appDependencies).settings(
      // Add your own project settings here      
    )

}
