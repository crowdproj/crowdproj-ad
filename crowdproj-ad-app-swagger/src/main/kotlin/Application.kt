package com.crowdproj.ad.app.swagger

import com.crowdproj.ad.app.swagger.configs.CwpAdAppSettings
import com.crowdproj.ad.app.swagger.plugins.configureRouting
import com.crowdproj.ad.app.swagger.plugins.initAppSettings
import io.ktor.server.application.*

fun main(args: Array<String>) = io.ktor.server.cio.EngineMain.main(args)
@Suppress("unused")
fun Application.module(appSettings: CwpAdAppSettings = initAppSettings()) {
    configureRouting(appSettings)
}
