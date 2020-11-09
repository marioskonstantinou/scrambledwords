import ProjectLibraries.coreDependencies

lazy val root = Project(id = "scrambledwords", base = file("."))
  .configure(ProjectUtils.setupProject)
  .settings(
    libraryDependencies ++= coreDependencies
  )