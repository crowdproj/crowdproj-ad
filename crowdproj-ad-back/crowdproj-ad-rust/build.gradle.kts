plugins {
}

version = rootProject.version

val builtFiles = "${layout.buildDirectory.get()}/dist"

@Suppress("UNUSED_VARIABLE")
val rustBuildTask: Task by tasks.creating {
    outputs.dir(builtFiles)
    val targetPlatform = listOf(
        "linux-amd64",
    )
    doLast {
        targetPlatform.forEach { platform ->
            val (os, arch) = platform.split("-")
            exec {
                workingDir = project.projectDir
                commandLine(
                    "make",
                    "build",
                    "PROJECT_VERSION=${project.version}",
                    "PROJECT_NAME=lib${project.name}.a",
                    "PROJECT_BUILD_DIR=$builtFiles"
                )
            }
        }
    }
}

val rustBuilds: Configuration by configurations.creating {
    isCanBeConsumed = true
    isCanBeResolved = false
}

artifacts {
    add(rustBuilds.name, tasks.named("rustBuildTask"))
}

