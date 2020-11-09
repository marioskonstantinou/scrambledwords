import sbt.Keys._
import sbt.{Def, _}
import sbtassembly.AssemblyKeys.{assembly, assemblyJarName, assemblyMergeStrategy}
import sbtassembly.AssemblyPlugin.autoImport.MergeStrategy
import sbtassembly.PathList

object CommonSettingsDefinition {
  val organisationString = "com.challenge"
  val scalaVersionString = "2.13.3"
  val versionString = "1.0.0"

  def commonSettings(projectName: String): Seq[Def.SettingsDefinition] = {
    Seq(
      organization := organisationString,
      scalaVersion := scalaVersionString,
      name := projectName,
      version := versionString,
      mainClass in Compile := Some("com.challenge.scrambled.Main"),
      fork in run := true,
      classLoaderLayeringStrategy := ClassLoaderLayeringStrategy.Flat,
      scalacOptions := Seq(
        "-deprecation",
        "-encoding",
        "UTF-8",
        "-language:higherKinds",
        "-language:postfixOps",
        "-feature",
        "-Ywarn-unused:imports",
        "-language:implicitConversions"
      ),
      test in assembly := {},
      scalacOptions in Test ~= { (options: Seq[String]) =>
        options filterNot (_ == "-deprecation")
      },
      assemblyMergeStrategy in assembly := {
        case PathList("META-INF", xs @ _*) => MergeStrategy.discard
        case x                             => MergeStrategy.first
      },
      assemblyJarName in assembly := {
        s"$projectName-$versionString.jar"
      }
    )

  }
}
