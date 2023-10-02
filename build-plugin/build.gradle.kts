import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
//    alias(libs.plugins.detekt)
}

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(libs.versions.jvm.language.get()))
//        vendor.set(JvmVendorSpec.AZUL)
    }
}

tasks.withType<KotlinCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(JvmTarget.valueOf("JVM_${libs.versions.jvm.compiler}"))
    }
}

tasks.withType<JavaCompile>().configureEach {
    sourceCompatibility = libs.versions.jvm.language.get()
    targetCompatibility = libs.versions.jvm.compiler.get()
}

gradlePlugin {
    plugins {
        register("convention") {
            id = "backend-plugin"
            implementationClass = "com.crowdproj.ad.build.plugin.ConventionPlugin"
        }
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // enable Ktlint formatting
//    add("detektPlugins", libs.plugin.detektFormatting)

    implementation(libs.plugin.kotlin)
//    implementation(libs.plugin.dokka)
    implementation(libs.plugin.binaryCompatibilityValidator)
    implementation(libs.plugin.mavenPublish)
}
