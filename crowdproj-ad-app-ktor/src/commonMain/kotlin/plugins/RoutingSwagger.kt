package com.crowdproj.ad.app.plugins

import configs.CwpAdAppSettings
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

expect fun Routing.swagger(appConfig: CwpAdAppSettings)