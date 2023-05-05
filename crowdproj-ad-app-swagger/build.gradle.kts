plugins {
    kotlin("plugin.serialization")
    kotlin("jvm")
    id("io.ktor.plugin")
}

val ktorVersion: String by project
val serializationVersion: String by project
val datetimeVersion: String by project
val coroutinesVersion: String by project
val logbackVersion: String by project
val slf4jVersion: String by project
val uuidVersion: String by project

fun ktorServer(module: String, version: String? = this@Build_gradle.ktorVersion): Any =
    "io.ktor:ktor-server-$module:$version"
fun ktorClient(module: String, version: String? = this@Build_gradle.ktorVersion): Any =
    "io.ktor:ktor-client-$module:$version"

application {
    mainClass.set("io.ktor.server.cio.EngineMain")
}

val apiSpec: Configuration by configurations.creating
val webjars: Configuration by configurations.creating
dependencies {
    val swaggerUiVersion: String by project
    val apiSpecVersion: String by project

    webjars("org.webjars:swagger-ui:$swaggerUiVersion")
    apiSpec(
        group = "com.crowdproj",
        name = "specs-v0",
        version = apiSpecVersion,
        classifier = "openapi",
        ext = "yaml"
    )

    implementation(ktorServer("config-yaml"))
    implementation(ktorServer("core"))
    implementation(ktorServer("cio"))
    implementation(ktorServer("auth"))
    implementation(ktorServer("cors"))

    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("ch.qos.logback:logback-access:$logbackVersion")

    implementation("org.slf4j:slf4j-api:$slf4jVersion")
    implementation(project(":crowdproj-lib-log"))

    testImplementation(kotlin("test-junit"))
    testImplementation(ktorServer("test-host"))
    testImplementation(ktorClient("content-negotiation"))
}

ktor {
    docker {
        jreVersion.set(io.ktor.plugin.features.JreVersion.JRE_17)
        localImageName.set(project.name)
        imageTag.set("${project.version}")
        portMappings.set(listOf(
            io.ktor.plugin.features.DockerPortMapping(
                80,
                8080,
                io.ktor.plugin.features.DockerPortMappingProtocol.TCP
            )
        ))

//        externalRegistry.set(
//            io.ktor.plugin.features.DockerImageRegistry.dockerHub(
//                appName = provider { "ktor-app" },
//                username = providers.environmentVariable("DOCKER_HUB_USERNAME"),
//                password = providers.environmentVariable("DOCKER_HUB_PASSWORD")
//            )
//        )
    }
}

tasks {
    @Suppress("UnstableApiUsage")
    withType<ProcessResources>().configureEach {
        println("VERSION: ${project.version} ${project.group} ${project.name}")
        // println("RESOURCES: ${this.name} ${this::class}")
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
        webjars.forEach { jar ->
//        emptyList<File>().forEach { jar ->
            val conf = webjars.resolvedConfiguration
//            println("JarAbsPa: ${jar.absolutePath}")
            val artifact = conf.resolvedArtifacts.find { it.file.toString() == jar.absolutePath } ?: return@forEach
            val upStreamVersion = artifact.moduleVersion.id.version.replace("(-[\\d.-]+)", "")
            copy {
                from(zipTree(jar))
                duplicatesStrategy = DuplicatesStrategy.EXCLUDE
                into(file("${buildDir}/webjars-content/${artifact.name}"))
            }
            with(this@configureEach) {
                this.duplicatesStrategy = DuplicatesStrategy.EXCLUDE
                from(
                    "${buildDir}/webjars-content/${artifact.name}/META-INF/resources/webjars/${artifact.name}/${upStreamVersion}"
                ) { into(artifact.name) }
                from(
                    "${buildDir}/webjars-content/${artifact.name}/META-INF/resources/webjars/${artifact.name}/${artifact.moduleVersion.id.version}"
                ) { into(artifact.name) }
            }
        }
    }
}

