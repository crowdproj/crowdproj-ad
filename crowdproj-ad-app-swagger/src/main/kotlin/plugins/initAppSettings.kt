package com.crowdproj.ad.app.swagger.plugins

import com.crowdproj.ad.app.swagger.configs.CwpAdAppSettings
import io.ktor.server.application.*

fun Application.initAppSettings(): CwpAdAppSettings {
    return CwpAdAppSettings(
        appUrls = environment.config.propertyOrNull("ktor.urls")?.getList() ?: emptyList(),
    )
}
