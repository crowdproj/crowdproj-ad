package com.crowdproj.ad.app.helpers

import com.crowdproj.ad.api.v1.mappers.fromApi
import com.crowdproj.ad.api.v1.mappers.toApi
import com.crowdproj.ad.api.v1.models.IRequestAd
import com.crowdproj.ad.api.v1.models.IResponseAd
import com.crowdproj.ad.common.CwpAdContext
import com.crowdproj.ad.common.helpers.asCwpAdError
import com.crowdproj.ad.common.helpers.fail
import com.crowdproj.ad.common.models.CwpAdRequestId
import configs.CwpAdAppSettings
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.callid.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.utils.io.charsets.*
import kotlinx.datetime.Clock

suspend inline fun <reified Rq : IRequestAd, reified Rs : IResponseAd> ApplicationCall.controllerHelperV1(appConfig: CwpAdAppSettings) {
    val endpoint: String = this.request.local.localAddress
    val requestId = this.callId
    val logger = application.log
    suitableCharset(Charsets.UTF_8)
    defaultTextContentType(ContentType.Application.Json.withCharset(Charsets.UTF_8))
    val ctx = CwpAdContext(
        timeStart = Clock.System.now(),
        requestId = requestId?.let { CwpAdRequestId(it) } ?: CwpAdRequestId.NONE,
    )
    try {
        logger.info("Started $endpoint request $requestId")
        val reqData = this.receive<Rq>()
        ctx.fromApi(reqData)
        appConfig.processor.exec(ctx)
        respond<Rs>(ctx.toApi() as Rs)
        logger.info("Finished $endpoint request $requestId")
    } catch (e: BadRequestException) {
        logger.error(
            "Bad request $requestId at $endpoint with exception", e
        )
        ctx.fail(
            e.asCwpAdError(
                code = "bad-request",
                group = "bad-request",
                message = "The request is not correct due to wrong/missing request parameters, body content or header values"
            )
        )
        appConfig.processor.exec(ctx)
        respond<Rs>(ctx.toApi() as Rs)
    } catch (e: Throwable) {
        logger.error(
            "Fail to handle $endpoint request $requestId with exception", e
        )
        ctx.fail(
            e.asCwpAdError(
                message = "Unknown error. We have been informed and dealing with the problem."
            )
        )
        appConfig.processor.exec(ctx)
        respond<Rs>(ctx.toApi() as Rs)
    }
}
