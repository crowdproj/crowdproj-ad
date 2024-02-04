package com.crowdproj.ad.app

import com.crowdproj.ad.api.v1.models.*
import kotlinx.serialization.json.Json
import kotlin.test.Test

class AdRequestSerializationTest {
    @Test
    fun test() {
        val req = AdCreateRequest(
            ad = AdCreateObject(
                title = "my title",
                description = "my description",
                adType = DealSide.DEMAND,
                visibility = AdVisibility.PUBLIC,
                productId = "23423423",
            ),
            debug = AdDebug(
                mode = AdRequestDebugMode.STUB,
                stub = AdRequestDebugStubs.SUCCESS,
            )
        )
        val json = Json.encodeToString(AdCreateRequest.serializer(), req)
        println("REQUEST: $json")
    }
}
