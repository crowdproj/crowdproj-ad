package com.crowdproj.ad.app.helpers

import com.crowdproj.ad.api.v1.mappers.fromApi
import com.crowdproj.ad.api.v1.mappers.toApi
import com.crowdproj.ad.api.v1.models.IRequestAd
import com.crowdproj.ad.api.v1.models.IResponseAd
import com.crowdproj.ad.common.CwpAdContext
import com.crowdproj.ad.common.models.CwpAdRequestId
import configs.CwpAdAppSettings
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.callid.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.utils.io.charsets.*
import kotlinx.datetime.Clock

suspend inline fun <reified Rq: IRequestAd, reified Rs: IResponseAd> ApplicationCall.controllerHelperV1(appConfig: CwpAdAppSettings) {
    val endpoint: String = this.request.local.localAddress
    val requestId = this.callId
    val logger = application.log
    val ctx = CwpAdContext(
        timeStart = Clock.System.now(),
        requestId = requestId?.let { CwpAdRequestId(it) } ?: CwpAdRequestId.NONE,
    )
    try {
        suitableCharset(Charsets.UTF_8)
        defaultTextContentType(ContentType.Application.Json.withCharset(Charsets.UTF_8))
        logger.info("Started $endpoint request $requestId")
        val reqData = this.receive<Rq>()
        ctx.fromApi(reqData)
//        ctx.adResponse = ctx.adRequest
        appConfig.processor.exec(ctx)
        respond<Rs>(ctx.toApi() as Rs)
        logger.info("Finished $endpoint request $requestId")
    } catch (e: Throwable) {
        logger.error(
            "Fail to handle $endpoint request $requestId with exception $e"
        )
//        val resp = block(requestId, e)
//        respond(resp)
    }
}
