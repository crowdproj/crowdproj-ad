package com.crowdproj.ad.app.plugins

import com.benasher44.uuid.uuid4
import com.crowdproj.ad.api.v1.models.*
import com.crowdproj.ad.app.controllers.controllerHelperV1
import configs.CwpAdAppSettings
import com.crowdproj.ad.logs.LogLevel
import com.crowdproj.ad.logs.log
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.callid.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.CORS
import io.ktor.server.routing.*
import kotlin.reflect.KClass

private val clazz: KClass<*> = Application::configureRouting::class
fun Application.configureRouting(appConfig: CwpAdAppSettings) {
    install(Routing)
    install(IgnoreTrailingSlash)
    install(CallId) {
        generate {
            "rq-${uuid4()}"
        }
    }
    install(CORS) {
        println("COORRRS: ${appConfig.appUrls}")
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
    install(ContentNegotiation) {
        json(appConfig.json)
    }
    routing {
        trace { application.log.trace(it.buildText()) }
        post("v1/create") {
            call.controllerHelperV1<AdCreateRequest, AdCreateResponse>(appConfig)
        }
        post("v1/read") {
            call.controllerHelperV1<AdReadRequest, AdReadResponse>(appConfig)
        }
        post("v1/update") {
            call.controllerHelperV1<AdUpdateRequest, AdUpdateResponse>(appConfig)
        }
        post("v1/delete") {
            call.controllerHelperV1<AdDeleteRequest, AdDeleteResponse>(appConfig)
        }
        post("v1/search") {
            call.controllerHelperV1<AdSearchRequest, AdSearchResponse>(appConfig)
        }
        post("v1/offers") {
            call.controllerHelperV1<AdOffersRequest, AdOffersResponse>(appConfig)
        }
    }
}
