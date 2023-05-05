package com.crowdproj.ad.app.swagger.configs

import kotlinx.serialization.json.Json

data class CwpAdAppSettings(
    val json: Json = Json,
    val appUrls: List<String> = listOf(),
)
