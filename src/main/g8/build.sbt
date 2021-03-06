ThisBuild / resolvers ++= Seq(
    "Apache Development Snapshot Repository" at "https://repository.apache.org/content/repositories/snapshots/",
    Resolver.mavenLocal,
    "Artifactory" at "http://codefeedr.joskuijpers.nl:8081/artifactory/sbt-dev-local/"
)

name := "$name$"

version := "$version$"

organization := "$organization$"

ThisBuild / scalaVersion := "$scala_version$"

val codefeedrVersion = "$codefeedr_version$"

val flinkVersion = "1.4.2"

val flinkDependencies = Seq(
  // When running the project locally use `Compile`
  // When running sbt assembly use `Provided`
  "org.apache.flink" %% "flink-scala" % flinkVersion % Provided,
  "org.apache.flink" %% "flink-streaming-scala" % flinkVersion % Provided)

val codefeedrDependencies = Seq(
  "org.codefeedr" %% "codefeedr-core" % "0.1-SNAPSHOT"
)

lazy val root = (project in file(".")).
  settings(
    libraryDependencies ++= flinkDependencies ++ codefeedrDependencies
  )

assembly / mainClass := Some("$organization$.Main")

// make run command include the provided dependencies
Compile / run  := Defaults.runTask(Compile / fullClasspath,
                                   Compile / run / mainClass,
                                   Compile / run / runner
                                  ).evaluated

// stays inside the sbt console when we press "ctrl-c" while a Flink programme executes with "run" or "runMain"
Compile / run / fork := true
Global / cancelable := true

// exclude Scala library from assembly
assembly / assemblyOption  := (assembly / assemblyOption).value.copy(includeScala = false)
