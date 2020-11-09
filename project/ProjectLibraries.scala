import ProjectLibraries.RuntimeDependencies._
import ProjectLibraries.TestDependencies._
import ProjectLibraryVersions._
import sbt._

object ProjectLibraries {
  object RuntimeDependencies {
    val PureConfig = "com.github.pureconfig" %% "pureconfig" % PureConfigVersion
    val Logback = "ch.qos.logback" % "logback-classic" % LogbackVersion
    val TypesafeLogging = "com.typesafe.scala-logging" %% "scala-logging" % TypesafeLoggingVersion
    val Scopt = "com.github.scopt" %% "scopt" % ScoptVersion
  }

  object TestDependencies {
    val Specs2 = "org.specs2" %% "specs2-core" % Specs2Version % Test
    val ScalaTest = "org.scalatest" %% "scalatest" % ScalaTestVersion % Test
  }

  val coreDependencies = Seq(
    Scopt,
    PureConfig,
    Logback,
    TypesafeLogging,
    Specs2,
    ScalaTest
  )
}
