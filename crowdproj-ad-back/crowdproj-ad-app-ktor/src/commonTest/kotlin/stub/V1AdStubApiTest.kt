package com.crowdproj.ad.app.stub

import com.crowdproj.ad.api.v1.cwpAdApiV1Json
import com.crowdproj.ad.api.v1.models.*
import com.crowdproj.ad.app.module
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals

class V1AdStubApiTest {

    @Test
    fun create() = v2TestApplication {  client ->
        val response = client.post("/v1/create") {
            val requestObj = AdCreateRequest(
                ad = AdCreateObject(
                    title = "Болт",
                    description = "КРУТЕЙШИЙ",
                    adType = DealSide.DEMAND,
                    visibility = AdVisibility.PUBLIC,
                ),
                debug = AdDebug(
                    mode = AdRequestDebugMode.STUB,
                    stub = AdRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            header("X-Request-ID", "12345")
            setBody(requestObj)
        }
        val responseObj = response.body<IResponseAd>() as AdCreateResponse
        assertEquals(200, response.status.value)
        assertEquals("666", responseObj.ad?.id)
    }

    @Test
    fun read() = v2TestApplication {  client ->
        val response = client.post("/v1/read") {
            val requestObj = AdReadRequest(
                ad = AdReadObject("666"),
                debug = AdDebug(
                    mode = AdRequestDebugMode.STUB,
                    stub = AdRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            header("X-Request-ID", "12345")
            setBody(requestObj)
        }
        val responseObj = response.body<IResponseAd>() as AdReadResponse
        assertEquals(200, response.status.value)
        assertEquals("666", responseObj.ad?.id)
    }

    @Test
    fun update() = v2TestApplication {  client ->
        val response = client.post("/v1/update") {
            val requestObj = AdUpdateRequest(
                ad = AdUpdateObject(
                    id = "666",
                    title = "Болт",
                    description = "КРУТЕЙШИЙ",
                    adType = DealSide.DEMAND,
                    visibility = AdVisibility.PUBLIC,
                ),
                debug = AdDebug(
                    mode = AdRequestDebugMode.STUB,
                    stub = AdRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            header("X-Request-ID", "12345")
            setBody(requestObj)
        }
        val responseObj = response.body<IResponseAd>() as AdUpdateResponse
        assertEquals(200, response.status.value)
        assertEquals("666", responseObj.ad?.id)
    }

    @Test
    fun delete() = v2TestApplication {  client ->
        val response = client.post("/v1/delete") {
            val requestObj = AdDeleteRequest(
                ad = AdDeleteObject(
                    id = "666",
                    lock = "123"
                ),
                debug = AdDebug(
                    mode = AdRequestDebugMode.STUB,
                    stub = AdRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            header("X-Request-ID", "12345")
            setBody(requestObj)
        }
        val responseObj = response.body<IResponseAd>() as AdDeleteResponse
        assertEquals(200, response.status.value)
        assertEquals("666", responseObj.ad?.id)
    }

    @Test
    fun search() = v2TestApplication {  client ->
        val response = client.post("/v1/search") {
            val requestObj = AdSearchRequest(
                adFilter = AdSearchFilter(),
                debug = AdDebug(
                    mode = AdRequestDebugMode.STUB,
                    stub = AdRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            header("X-Request-ID", "12345")
            setBody(requestObj)
        }
        val responseObj = response.body<IResponseAd>() as AdSearchResponse
        assertEquals(200, response.status.value)
        assertEquals("d-666-01", responseObj.ads?.first()?.id)
    }

    @Test
    fun offers() = v2TestApplication {  client ->
        val response = client.post("/v1/offers") {
            val requestObj = AdOffersRequest(
                ad = AdReadObject(
                    id = "666"
                ),
                debug = AdDebug(
                    mode = AdRequestDebugMode.STUB,
                    stub = AdRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            header("X-Request-ID", "12345")
            setBody(requestObj)
        }
        val responseObj = response.body<IResponseAd>() as AdOffersResponse
        assertEquals(200, response.status.value)
        assertEquals("s-666-01", responseObj.ads?.first()?.id)
    }

    private fun v2TestApplication(function: suspend (HttpClient) -> Unit): Unit = testApplication {
        application { module() }
        val client = createClient {
            install(ContentNegotiation) {
                json(cwpAdApiV1Json)
            }
        }
        function(client)
    }

}
