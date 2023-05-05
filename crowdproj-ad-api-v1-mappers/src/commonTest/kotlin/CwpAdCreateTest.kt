package com.crowdproj.ad.api.v1.mappers

import com.crowdproj.ad.api.v1.models.*
import com.crowdproj.ad.common.CwpAdContext
import com.crowdproj.ad.common.models.*
import com.crowdproj.ad.common.stubs.CwpStubs
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertIs

class CwpAdCreateTest {
    @Test
    fun request() {
        val api = AdCreateRequest(
            debug = AdDebug(
                mode = AdRequestDebugMode.STUB,
                stub = AdRequestDebugStubs.BAD_DESCRIPTION,
            ),
            ad = AdCreateObject(
                title = "ti",
                description = "des",
                adType = DealSide.DEMAND,
                visibility = AdVisibility.PUBLIC,
                productId = "98798",
            ),
        )
        val ctx = CwpAdContext()
        ctx.fromApi(api as IRequestAd)
        assertEquals(CwpAdWorkMode.STUB, ctx.workMode)
        assertEquals(CwpStubs.BAD_DESCRIPTION, ctx.stubCase)
        assertEquals("ti", ctx.adRequest.title)
        assertEquals("des", ctx.adRequest.description)
        assertEquals(CwpAdDealSide.DEMAND, ctx.adRequest.adType)
        assertEquals(CwpAdVisibility.VISIBLE_PUBLIC, ctx.adRequest.visibility)
        assertEquals("98798", ctx.adRequest.productId.asString())
        assertEquals(CwpAdCommand.CREATE, ctx.command)
    }
    @Test
    fun response() {
        val ctx = CwpAdContext(
            requestId = CwpAdRequestId("111"),
            command = CwpAdCommand.CREATE,
            adResponse = CwpAd(
                id = CwpAdId("123"),
                lock = CwpAdLock("234"),
                title = "ti",
                description = "des",
                ownerId = CwpAdUserId("567"),
                adType = CwpAdDealSide.DEMAND,
                visibility = CwpAdVisibility.VISIBLE_PUBLIC,
                productId = CwpAdProductId("789"),
                permissionsClient = mutableSetOf(
                    CwpAdPermissionClient.DELETE,
                    CwpAdPermissionClient.READ,
                    CwpAdPermissionClient.UPDATE,
                )
            )
        )
        val api = ctx.toApi()
        assertIs<AdCreateResponse>(api)
        assertEquals("111", api.requestId)
        assertEquals("123", api.ad?.id)
        assertEquals("234", api.ad?.lock)
        assertEquals("567", api.ad?.ownerId)
        assertEquals("ti", api.ad?.title)
        assertEquals("des", api.ad?.description)
        assertEquals(DealSide.DEMAND, api.ad?.adType)
        assertEquals(AdVisibility.PUBLIC, api.ad?.visibility)
        assertEquals("789", api.ad?.productId)
        assertContains(api.ad?.permissions ?: emptySet(), AdPermissions.READ)
        assertContains(api.ad?.permissions ?: emptySet(), AdPermissions.UPDATE)
        assertContains(api.ad?.permissions ?: emptySet(), AdPermissions.DELETE)
    }
}
