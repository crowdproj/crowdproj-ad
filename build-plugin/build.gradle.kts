import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
//    alias(libs.plugins.detekt)
}

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(19))
        vendor.set(JvmVendorSpec.AZUL)
    }
}

tasks.withType<KotlinCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_11)
    }
}

tasks.withType<JavaCompile>().configureEach {
    sourceCompatibility = JavaVersion.VERSION_11.toString()
    targetCompatibility = JavaVersion.VERSION_11.toString()
}

gradlePlugin {
    plugins {
        register("convention") {
            id = "cache4k.convention"
            implementationClass = "io.github.reactivecircus.cache4k.buildlogic.convention.ConventionPlugin"
        }
    }
}

dependencies {
    // TODO: remove when this fixed
    //  https://github.com/gradle/gradle/issues/15383#issuecomment-779893192
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))

    // enable Ktlint formatting
//    add("detektPlugins", libs.plugin.detektFormatting)

    implementation(libs.plugin.kotlin)
    implementation(libs.plugin.dokka)
    implementation(libs.plugin.binaryCompatibilityValidator)
    implementation(libs.plugin.mavenPublish)
}
