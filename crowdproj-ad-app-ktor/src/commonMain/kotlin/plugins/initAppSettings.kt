package com.crowdproj.ad.app.plugins

import configs.CwpAdAppSettings
import io.ktor.server.application.*

fun Application.initAppSettings(): CwpAdAppSettings {
    return CwpAdAppSettings(
        appUrls = environment.config.propertyOrNull("ktor.urls")?.getList() ?: emptyList(),
    )
}
