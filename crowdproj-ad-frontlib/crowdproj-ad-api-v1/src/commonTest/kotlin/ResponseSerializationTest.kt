package com.crowdproj.ad.api.v1

import com.crowdproj.ad.front.api.v1.models.*
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class ResponseSerializationTest {
    private val response: IResponseAd = AdCreateResponse(
        requestId = "123",
        ad = AdResponseObject(
            title = "ad title",
            description = "ad description",
            adType = DealSide.DEMAND,
            visibility = AdVisibility.PUBLIC,
        )
    )

    @Test
    fun serialize() {
        val json = Json.encodeToString(IResponseAd.serializer(), response)

        println(json)

        assertContains(json, Regex("\"title\":\\s*\"ad title\""))
        assertContains(json, Regex("\"responseType\":\\s*\"create\""))
    }

    @Test
    fun deserialize() {
        val json = Json.encodeToString(IResponseAd.serializer(), response)
        val obj = Json.decodeFromString(IResponseAd.serializer(), json) as AdCreateResponse

        assertEquals(response, obj)
    }
}
