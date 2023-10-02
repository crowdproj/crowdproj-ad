package com.crowdproj.ad.app.plugins

import com.crowdproj.ad.app.configs.CwpAdAppSettings
import com.crowdproj.ad.logs.LogLevel
import com.crowdproj.ad.logs.log
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.cors.routing.*
import kotlin.reflect.KClass

private val clazz: KClass<*> = Application::initCors::class
fun Application.initCors(appConfig: CwpAdAppSettings) {
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
            val host = when (split.size) {
                2 -> split[1].split("/")[0].apply { log(mes = "COR: $this", clazz = clazz, level = LogLevel.INFO) } to
                        listOf(split[0])

                1 -> split[0].split("/")[0].apply { log(mes = "COR: $this", clazz = clazz, level = LogLevel.INFO) } to
                        listOf("http", "https")

                else -> null
            }
            println("ALLOW_HOST: $host")
        }
        allowHost("*")
    }

}
