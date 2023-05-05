package com.crowdproj.ad.app.swagger.plugins

import com.crowdproj.ad.app.swagger.configs.CwpAdAppSettings
import com.crowdproj.ad.logs.LogLevel
import com.crowdproj.ad.logs.log
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.routing.*
import kotlin.reflect.KClass

private val clazz: KClass<*> = Application::configureRouting::class
fun Application.configureRouting(appConfig: CwpAdAppSettings) {
    install(Routing)
    install(IgnoreTrailingSlash)
    install(CORS) {
        allowNonSimpleContentTypes = true
        allowSameOrigin = true
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Post)
        allowMethod(HttpMethod.Get)
        allowHeader("*")
        appConfig.appUrls.forEach {
            val split = it.split("://")
            println("$split")
            when (split.size) {
                2 -> allowHost(
                    split[1].split("/")[0].apply { log(mes = "COR: $this", clazz = clazz, level = LogLevel.INFO) },
                    listOf(split[0])
                )

                1 -> allowHost(
                    split[0].split("/")[0].apply { log(mes = "COR: $this", clazz = clazz, level = LogLevel.INFO) },
                    listOf("http", "https")
                )
            }
        }
    }
    routing {
        trace { application.log.trace(it.buildText()) }
        swagger(appConfig)
    }
}
