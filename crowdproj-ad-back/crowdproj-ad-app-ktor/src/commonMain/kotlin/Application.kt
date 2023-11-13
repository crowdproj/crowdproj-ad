package com.crowdproj.ad.app

import com.crowdproj.ad.app.configs.CwpAdAppSettings
import com.crowdproj.ad.app.plugins.configureRouting
import com.crowdproj.ad.app.plugins.initAppSettings
import io.ktor.server.application.*

//fun main(args: Array<String>) = io.ktor.server.cio.EngineMain.main(args)
expect fun main(args: Array<String>)

@Suppress("unused")
fun Application.module(appSettings: CwpAdAppSettings = initAppSettings()) {
    configureRouting(appSettings)
}
