package com.crowdproj.ad.api.v1.mappers

import com.crowdproj.ad.api.v1.models.*
import com.crowdproj.ad.common.CwpAdContext
import com.crowdproj.ad.common.models.*
import com.crowdproj.ad.common.stubs.CwpStubs
import kotlin.test.*

class CwpAdOffersTest {
    @Test
    fun request() {
        val api = AdOffersRequest(
            debug = AdDebug(
                mode = AdRequestDebugMode.STUB,
                stub = AdRequestDebugStubs.BAD_DESCRIPTION,
            ),
            ad = AdReadObject(
                id = "123",
            ),
        )
        val ctx = CwpAdContext()
        ctx.fromApi(api as IRequestAd)
        assertEquals(CwpAdWorkMode.STUB, ctx.workMode)
        assertEquals(CwpStubs.BAD_DESCRIPTION, ctx.stubCase)
        assertEquals("123", ctx.adRequest.id.asString())
        assertEquals(CwpAdCommand.OFFERS, ctx.command)
    }

    @Test
    fun response() {
        val ctx = CwpAdContext(
            requestId = CwpAdRequestId("111"),
            command = CwpAdCommand.OFFERS,
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
            ),
            adsResponse = mutableListOf(
                CwpAd(
                    id = CwpAdId("a123"),
                    lock = CwpAdLock("a234"),
                    title = "ati",
                    description = "ades",
                    ownerId = CwpAdUserId("a567"),
                    adType = CwpAdDealSide.SUPPLY,
                    visibility = CwpAdVisibility.VISIBLE_TO_OWNER,
                    productId = CwpAdProductId("a789"),
                    permissionsClient = mutableSetOf(
                        CwpAdPermissionClient.DELETE,
                        CwpAdPermissionClient.READ,
                        CwpAdPermissionClient.MAKE_VISIBLE_GROUP,
                    )
                )
            )
        )
        val api = ctx.toApi()
        assertIs<AdOffersResponse>(api)
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
        val ad = api.ads?.first()
        assertNotNull(ad)
        assertEquals("a123", ad.id)
        assertEquals("a234", ad.lock)
        assertEquals("a567", ad.ownerId)
        assertEquals("ati", ad.title)
        assertEquals("ades", ad.description)
        assertEquals(DealSide.SUPPLY, ad.adType)
        assertEquals(AdVisibility.OWNER_ONLY, ad.visibility)
        assertEquals("a789", ad.productId)
        assertContains(ad.permissions ?: emptySet(), AdPermissions.READ)
        assertContains(ad.permissions ?: emptySet(), AdPermissions.MAKE_VISIBLE_GROUP)
        assertContains(ad.permissions ?: emptySet(), AdPermissions.DELETE)
    }
}
