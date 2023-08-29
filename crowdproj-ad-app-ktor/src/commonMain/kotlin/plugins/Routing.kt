package com.crowdproj.ad.app.plugins

import com.crowdproj.ad.api.v1.models.*
import com.crowdproj.ad.app.helpers.controllerHelperV1
import com.crowdproj.ad.app.configs.CwpAdAppSettings
import io.ktor.server.application.*
import io.ktor.server.routing.*
import kotlin.io.encoding.ExperimentalEncodingApi
import kotlin.reflect.KClass

private val clazz: KClass<*> = Application::configureRouting::class
@OptIn(ExperimentalEncodingApi::class)
fun Application.configureRouting(appConfig: CwpAdAppSettings) {
    initRest(appConfig)
    initCors(appConfig)
    routing {
//        trace { application.log.trace(it.buildText()) }

        swagger(appConfig)

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

