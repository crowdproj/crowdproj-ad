package com.crowdproj.ad.app.configs

import com.crowdproj.ad.api.v1.cwpAdApiV1Json
import com.crowdproj.ad.biz.CwpAdProcessor
import com.crowdproj.ad.common.config.CwpAdCorSettings
import io.ktor.client.engine.*
import io.ktor.client.engine.cio.*
import kotlinx.serialization.json.Json

data class CwpAdAppSettings(
    var clientEngine: HttpClientEngine = CIO.create(),
    val json: Json = cwpAdApiV1Json,
    val corSettings: CwpAdCorSettings = CwpAdCorSettings(),
    val appUrls: List<String> = listOf(),
    val processor: CwpAdProcessor = CwpAdProcessor(corSettings)
)
