package com.crowdproj.ad.app

import configs.CwpAdAppSettings
import com.crowdproj.ad.app.plugins.configureRouting
import io.ktor.client.engine.*
import io.ktor.client.engine.cio.*
import io.ktor.server.application.*

fun main(args: Array<String>) = io.ktor.server.cio.EngineMain.main(args)
@Suppress("unused")
fun Application.module(appSettings: CwpAdAppSettings = CwpAdAppSettings()) {
    configureRouting(appSettings)
}
