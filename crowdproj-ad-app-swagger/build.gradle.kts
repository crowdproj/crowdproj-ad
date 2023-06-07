import org.apache.xerces.impl.dv.util.Base64
import org.jetbrains.kotlin.incremental.createDirectory

plugins {
    kotlin("multiplatform")
}

val apiVersion = "v1"
val apiSpec: Configuration by configurations.creating
val apiSpecVersion: String by project
dependencies {
    apiSpec(
        group = "com.crowdproj",
        name = "specs-v1",
        version = apiSpecVersion,
        classifier = "openapi",
        ext = "yaml"
    )
}

val embeddings = "$buildDir/generate-resources/main/src/commonMain/kotlin"

kotlin {
    jvm { withJava() }
    linuxX64 { }

    sourceSets {
        val serializationVersion: String by project

        @Suppress("UNUSED_VARIABLE")
        val commonMain by getting {

            kotlin.srcDirs(embeddings)
            dependencies {
                implementation(kotlin("stdlib-common"))

                implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:$serializationVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")
            }
        }

        @Suppress("UNUSED_VARIABLE")
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }

        @Suppress("UNUSED_VARIABLE")
        val jvmMain by getting {
            dependencies {
            }
        }
    }
}

afterEvaluate {
    tasks {
        val prepareSwagger by creating(Copy::class) {
            destinationDir = file("${buildDir}/swagger")
            from("$rootDir/specs") {
                into("specs")
                filter {
                    // Устанавливаем версию в сваггере
                    it.replace("\${VERSION_APP}", project.version.toString())
                }
            }
            from(apiSpec.asPath) {
                into("specs")
                rename { "base.yaml" }
            }
            outputs.dir(destinationDir)
        }
        val generateResourceKt by creating {
            dependsOn(prepareSwagger)
            file(embeddings).createDirectory()
            val resPath = prepareSwagger.destinationDir
            inputs.dir(resPath)
            var cntr = 0
            doLast {
                val resources = fileTree(resPath).files
                    .map { fileContent ->
                        file("$embeddings/Resource_${cntr}.kt").apply(File::createNewFile).writeText(
                            """
                        package com.crowdproj.ad.app.resources
                        
                        val RES_${cntr} = "${Base64.encode(fileContent.readBytes())}"
                    """.trimIndent()
                        )
                        fileContent.relativeTo(resPath).toString() to cntr++
                    }
                file("$embeddings/Resources.kt").apply(File::createNewFile).writeText(
                    """
                    package com.crowdproj.ad.app.resources
                    
                    val RESOURCES = mapOf(
                        ${
                        resources.joinToString(",\n                        ") {
                            "\"${it.first}\" to RES_${it.second}"
                        }
                    }
                    )
                """.trimIndent()
                )
            }
        }

        filter { it.name.startsWith("compile") }.forEach {
            println("COMPILE: $it")
            it.dependsOn(generateResourceKt)
        }
    }
}

