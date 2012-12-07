import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "c-ration"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
//      "com.typesafe" % "config" % "1.0.0"
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = JAVA).settings(
      // Add your own project settings here      
    )

}