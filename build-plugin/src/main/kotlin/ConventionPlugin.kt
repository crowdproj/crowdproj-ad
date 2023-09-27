package com.crowdproj.ad.build.plugin

import com.vanniktech.maven.publish.MavenPublishBaseExtension
import com.vanniktech.maven.publish.MavenPublishPlugin
import com.vanniktech.maven.publish.SonatypeHost
import kotlinx.validation.BinaryCompatibilityValidatorPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.jvm.toolchain.JvmVendorSpec
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal class ConventionPlugin : Plugin<Project> {
    override fun apply(project: Project) = with(project) {
        // apply dokka plugin
//        pluginManager.apply(DokkaPlugin::class.java)

        if (rootProject == this) {
            configureRootProject()
        } else {
            configureSubproject()
        }
    }
}

private fun Project.configureRootProject() {
//    tasks.withType<DokkaMultiModuleTask>().configureEach {
//        val apiDir = rootDir.resolve("docs/api")
//        outputDirectory.set(apiDir)
//        doLast {
//            apiDir.resolve("-modules.html").renameTo(apiDir.resolve("index.html"))
//        }
//    }

    // configure compatibility validator
    pluginManager.apply(BinaryCompatibilityValidatorPlugin::class.java)
}

private fun Project.configureSubproject() {
    // configure KMP library project
    pluginManager.apply("org.jetbrains.kotlin.multiplatform")
    group = property("GROUP") as String
    version = property("VERSION_NAME") as String

    plugins.withId("org.jetbrains.kotlin.multiplatform") {
        extensions.configure<KotlinMultiplatformExtension> {
            explicitApi()
            configureTargets(this@configureSubproject)
            sourceSets.configureEach {
                languageSettings.apply {
                    languageVersion = "1.9"
                    progressiveMode = true
                    enableLanguageFeature("NewInference")
                    optIn("kotlin.time.ExperimentalTime")
                }
            }
        }
    }

    // configure detekt
//    pluginManager.apply(DetektPlugin::class.java)
//    dependencies.add("detektPlugins", the<LibrariesForLibs>().plugin.detektFormatting)

    // configure test
//    tasks.withType<Test>().configureEach {
//        useJUnitPlatform()
//        testLogging {
//            events("passed", "skipped", "failed")
//        }
//    }

    // configure publishing
    pluginManager.apply(MavenPublishPlugin::class.java)
    extensions.configure<MavenPublishBaseExtension> {
        publishToMavenCentral(SonatypeHost.S01, automaticRelease = true)
//        signAllPublications()
    }
}

@Suppress("LongMethod", "MagicNumber")
private fun KotlinMultiplatformExtension.configureTargets(project: Project) {
    targets {
        jvmToolchain {
            languageVersion.set(JavaLanguageVersion.of(20))
            vendor.set(JvmVendorSpec.AZUL)
        }

        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        targetHierarchy.default {
//            group("jvmAndIos") {
//                withJvm()
//            }
            group("nonJvm") {
                withLinuxX64()
                withLinuxArm64()
            }
        }

        jvm {
            compilations.configureEach {
                compilerOptions.configure {
                    jvmTarget.set(JvmTarget.JVM_11)
                    freeCompilerArgs.addAll(
                        "-Xjvm-default=all"
                    )
                }
            }
            val main = compilations.getByName("main")
//            compilations.create("lincheck") {
//                defaultSourceSet {
//                    dependencies {
//                        implementation(main.compileDependencyFiles + main.output.classesDirs)
//                    }
//                }
//                project.tasks.register<Test>("jvmLincheck") {
//                    classpath = compileDependencyFiles + runtimeDependencyFiles + output.allOutputs
//                    testClassesDirs = output.classesDirs
//                    useJUnitPlatform()
//                    testLogging {
//                        events("passed", "skipped", "failed")
//                    }
//                    jvmArgs(
//                        "--add-opens", "java.base/jdk.internal.misc=ALL-UNNAMED",
//                        "--add-opens", "java.base/java.lang=ALL-UNNAMED",
//                        "--add-exports", "java.base/jdk.internal.util=ALL-UNNAMED",
//                        "--add-exports", "java.base/jdk.internal.vm=ALL-UNNAMED",
//                        "--add-exports", "java.base/jdk.internal.access=ALL-UNNAMED",
//                    )
//                }
//            }
        }
        linuxX64()
        linuxArm64()
    }
}
