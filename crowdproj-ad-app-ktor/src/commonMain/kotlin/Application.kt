package com.crowdproj.ad.app

import com.crowdproj.ad.app.plugins.configureRouting
import com.crowdproj.ad.app.plugins.initAppSettings
import configs.CwpAdAppSettings
import io.ktor.server.application.*

//fun main(args: Array<String>) = io.ktor.server.cio.EngineMain.main(args)
expect fun main(args: Array<String>)

@Suppress("unused")
fun Application.module(appSettings: CwpAdAppSettings = initAppSettings()) {
    println("CONFIGS ${environment.config.toMap().size}")
    environment.config.toMap().forEach {
        println("${it.key} -> ${it.value}")
    }
    configureRouting(appSettings)
}
