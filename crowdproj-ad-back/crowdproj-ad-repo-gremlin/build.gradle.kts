import org.jetbrains.kotlin.gradle.internal.ensureParentDirsCreated

plugins {
    id("backend-convention")
}

val generatedPath: Provider<Directory> = layout.buildDirectory.dir("generated/main/kotlin")
kotlin {
    jvm { withJava() }
    sourceSets {
        val commonMain by getting {
            kotlin.srcDirs(generatedPath)
            dependencies {
                implementation(libs.coroutines.core)
                implementation(libs.uuid)

                implementation(project(":crowdproj-ad-common"))
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.test.containers)
                implementation(project(":crowdproj-ad-repo-tests"))

                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(libs.gremlin)
                implementation(libs.arcadedb.engine)
                implementation(libs.arcadedb.network)
                implementation(libs.arcadedb.gremlin)
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation(libs.test.containers)
            }
        }
        val nativeMain by getting {

        }
    }
}
val arcadeDbVersion: String = libs.versions.arcadedb.get()

tasks {
    val gradleConstants by creating {
        file(generatedPath.get().file("GradleConstants.kt")).apply {
            ensureParentDirsCreated()
            writeText(
                """
                    package ${project.group}.backend.repository.gremlin

                    const val ARCADEDB_VERSION = "$arcadeDbVersion"
                """.trimIndent()
            )
        }
    }
//    withType(CompileTask::class) {

//    }
    withType(AbstractNativeCompileTask::class) {
        dependsOn(gradleConstants)
    }
//    compileKotlin.get().dependsOn(gradleConstants)
}
