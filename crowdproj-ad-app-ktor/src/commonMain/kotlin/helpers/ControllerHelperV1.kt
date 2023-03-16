package com.crowdproj.ad.app.controllers

import com.crowdproj.ad.common.models.CwpAdRequestId
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.utils.io.charsets.*

suspend fun ApplicationCall.controllerHelperV1(endpoint: String, block: suspend (CwpAdRequestId, Throwable?) -> Any) {
    val requestId = request.call.request
        .queryParameters["requestId"]
        ?.let { CwpAdRequestId(it) }
        ?: CwpAdRequestId.NONE
    val logger = application.log
    try {
        suitableCharset(Charsets.UTF_8)
        defaultTextContentType(ContentType.Application.Json.withCharset(Charsets.UTF_8))
        logger.info("Started $endpoint request $requestId")
        val resp = block(requestId, null)
        respond(resp)
        logger.info("Finished $endpoint request $requestId with config $resp")
    } catch (e: Throwable) {
        logger.error(
            "Fail to handle $endpoint request $requestId with exception $e"
        )
        val resp = block(requestId, e)
        respond(resp)
    }
}
