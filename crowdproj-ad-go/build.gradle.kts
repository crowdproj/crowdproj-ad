plugins {
}

val builtFiles = "${project.buildDir}/plugin"

val goBuildTask: Task by tasks.creating {
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
                    "GOOS=${os}",
                    "GOARCH=${arch}",
                    "PROJECT_NAME=lib${project.name}.a",
                    "PROJECT_BUILD_DIR=$builtFiles"
                )
            }
        }
    }
}

val goBuilds: Configuration by configurations.creating {
    isCanBeConsumed = true
    isCanBeResolved = false
}

artifacts {
    add(goBuilds.name, tasks.named("goBuildTask"))
}

