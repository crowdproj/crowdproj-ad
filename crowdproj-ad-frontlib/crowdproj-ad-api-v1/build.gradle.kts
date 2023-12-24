plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.crowdproj.generator)
}

group = rootProject.group
version = rootProject.version

val specDir = "${layout.buildDirectory.get()}/specs"
val apiVersion = "v1"
val apiSpec: Configuration by configurations.creating
dependencies {
    apiSpec(
        group = "com.crowdproj",
        name = "specs-v1",
        version = baselibs.versions.api.spec.base.get(),
        classifier = "openapi",
        ext = "yaml"
    )
}

kotlin {
    jvm { withJava() }
    js {
        browser {}
    }
//    linuxX64 {}
//    linuxArm64 {}

    sourceSets {
        val commonMain by getting {
            kotlin.srcDirs("${layout.buildDirectory.get()}/generate-resources/main/src/commonMain/kotlin")
            dependencies {
                implementation(kotlin("stdlib-common"))

                implementation(libs.kotlinx.serialization.core)
                implementation(libs.kotlinx.serialization.json)
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }

        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }
        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }
    }
}


tasks {
    val getSpecs: Task by creating(Copy::class) {
        group = "openapi tools"
        from("../../specs")
        from(apiSpec) {
            duplicatesStrategy = DuplicatesStrategy.INCLUDE
            rename { "base.yaml" }
        }
        into(specDir)
    }
    this.openApiGenerate {
        dependsOn(getSpecs)
        mustRunAfter("compileCommonMainKotlinMetadata")
    }
    filter { it.name.startsWith("compile") }.forEach {
        it.dependsOn(openApiGenerate)
    }
}

crowdprojGenerate {
    packageName.set("${project.group}.api.v1")
    inputSpec.set("${specDir}/spec-crowdproj-ad-$apiVersion.yaml")
}

//afterEvaluate {
//    val openApiGenerate = tasks.getByName("openApiGenerate")
//    tasks.filter { it.name.startsWith("compile") }.forEach {
//        it.dependsOn(openApiGenerate)
//    }
//    tasks.filter { it.name.endsWith("Elements") }.forEach {
//        it.dependsOn(openApiGenerate)
//    }
//}
