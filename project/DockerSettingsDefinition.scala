import com.typesafe.sbt.GitPlugin.autoImport.git
import sbt.Keys._
import sbt.{Def, _}
import sbtassembly.AssemblyKeys
import sbtdocker.DockerPlugin.autoImport.{Dockerfile, docker, dockerfile}

object DockerSettingsDefinition {

  val dockerDependencies
    : Def.SettingsDefinition = docker := (docker dependsOn (AssemblyKeys.assembly in AssemblyKeys.assembly)).value

  val scrambledDockerImageDefinition = dockerfile in docker := {
    val artifact =
      (AssemblyKeys.assemblyOutputPath in AssemblyKeys.assembly in AssemblyKeys.assembly).value
    val artifactTargetPath = s"/app/${artifact.name}"
    val scriptsTargetPath = "/app/scripts"
    val entryPointPath = s"$scriptsTargetPath/scrmabled-strings.sh"

    new Dockerfile {
      from("openjdk:8-jdk-alpine")
      run("apk", "update")
      run("apk", "--no-cache", "add", "bash")
      run("apk", "--no-cache", "add", "coreutils")
      copy(baseDirectory(_ / "scrmabled-strings.sh").value, entryPointPath)
      env("PATH", "$PATH:/app/bin")
      env("SBT_OPTS", "-XX:MaxMetaspaceSize=4g")
      env("BUILD_JAR_NAME", artifactTargetPath)
      env(
        "JAR_VERSION",
        s"${version.value}"
      )
      env("GIT_BRANCH", s"${git.gitCurrentBranch.value}")
      env("GIT_COMMIT_ID", git.gitHeadCommit.value.getOrElse("").trim)
      add(artifact, artifactTargetPath)
      run("mkdir", "-p", "/app/data")
      run("chmod", "+x", entryPointPath)
      entryPoint(entryPointPath)
    }
  }

}
