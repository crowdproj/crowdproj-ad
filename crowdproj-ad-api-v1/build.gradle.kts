plugins {
    kotlin("multiplatform")
    id("com.crowdproj.generator")
    kotlin("plugin.serialization")
}

val apiVersion = "v1"
val apiSpec: Configuration by configurations.creating
val apiSpecVersion: String by project
dependencies {
    apiSpec(
        group = "com.crowdproj",
        name = "specs-v0",
        version = apiSpecVersion,
        classifier = "openapi",
        ext = "yaml"
    )
}

kotlin {
    jvm { withJava() }
    js(IR) {
        browser {}
    }
    linuxX64 { }

    sourceSets {
        val serializationVersion: String by project

        @Suppress("UNUSED_VARIABLE")
        val commonMain by getting {

            kotlin.srcDirs("$buildDir/generate-resources/main/src/commonMain/kotlin")
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
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }
        @Suppress("UNUSED_VARIABLE")
        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }
    }
}

/**
 * Настраиваем генерацию здесь
 */
openApiGenerate {
    val openapiGroup = "${rootProject.group}.api.$apiVersion"
    generatorName.set("kotlin-crowdproj") // Это и есть активный генератор
    packageName.set(openapiGroup)
    globalProperties.set(mapOf(
    ))
    apiPackage.set("$openapiGroup.api")
    modelPackage.set("$openapiGroup.models")
    invokerPackage.set("$openapiGroup.invoker")
    inputSpec.set("${project.buildDir}/spec-crowdproj-ad-$apiVersion.yaml")
    library.set("multiplatform")
//    templateDir.set("$projectDir/templates")

    /**
     * Здесь указываем, что нам нужны только модели, все остальное не нужно
     */
    globalProperties.set(mapOf(
//        "debugModels" to "true",
        "models" to "",
        "modelDocs" to "false",
    ))

    /**
     * Настройка дополнительных параметров из документации по генератору
     * https://github.com/OpenAPITools/openapi-generator/blob/master/docs/generators/kotlin.md
     */
    configOptions.set(
        mapOf(
            "dateLibrary" to "string",
            "enumPropertyNaming" to "UPPERCASE",
            "collectionType" to "list",
        )
    )
}

val getSpecs: Task by tasks.creating {
    doFirst {
        copy {
            from("${rootProject.projectDir}/specs")
            into(project.buildDir.toString())
        }
        copy {
            from(apiSpec.asPath)
            into(project.buildDir.toString())
            rename { "base.yaml" }
        }
    }
}

tasks {
    this.openApiGenerate {
        dependsOn(getSpecs)
    }
}

afterEvaluate {
    val openApiGenerate = tasks.getByName("openApiGenerate")
    tasks.filter { it.name.startsWith("compile") }.forEach {
        it.dependsOn(openApiGenerate)
    }
    tasks.filter { it.name.endsWith("Elements") }.forEach {
        it.dependsOn(openApiGenerate)
    }
}
