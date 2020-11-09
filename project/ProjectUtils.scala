import com.typesafe.sbt.packager.archetypes.JavaAppPackaging
import com.typesafe.sbt.packager.archetypes.scripts.AshScriptPlugin
import com.typesafe.sbt.{GitBranchPrompt, GitVersioning}
import sbtdocker.DockerPlugin

object ProjectUtils {

  def setupProject(project: sbt.Project): sbt.Project =
    project
      .settings(CommonSettingsDefinition.commonSettings(project.id): _*)
      .enablePlugins(JavaAppPackaging)
      .enablePlugins(
        DockerPlugin,
        GitVersioning,
        GitBranchPrompt,
        AshScriptPlugin
      )
      .settings(DockerSettingsDefinition.scrambledDockerImageDefinition: _*)
      .settings(DockerSettingsDefinition.dockerDependencies)

}
