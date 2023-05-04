package com.crowdproj.ad.api.v1.mappers

import com.crowdproj.ad.api.v1.models.*
import com.crowdproj.ad.common.CwpAdContext
import com.crowdproj.ad.common.models.*
import com.crowdproj.ad.common.stubs.CwpStubs
import kotlin.test.*

class CwpAdSearchTest {
    @Test
    fun request() {
        val api = AdSearchRequest(
            requestId = "1234",
            debug = AdDebug(
                mode = AdRequestDebugMode.STUB,
                stub = AdRequestDebugStubs.BAD_DESCRIPTION,
            ),
            adFilter = AdSearchFilter(
                searchString = "one",
            ),
        )
        val ctx = CwpAdContext()
        ctx.fromApi(api as IRequestAd)
        assertEquals("1234", ctx.requestId.asString())
        assertEquals(CwpAdWorkMode.STUB, ctx.workMode)
        assertEquals(CwpStubs.BAD_DESCRIPTION, ctx.stubCase)
        assertEquals("one", ctx.adFilterRequest.searchString)
        assertEquals(CwpAdCommand.SEARCH, ctx.command)
    }

    @Test
    fun response() {
        val ctx = CwpAdContext(
            requestId = CwpAdRequestId("111"),
            command = CwpAdCommand.SEARCH,
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
        assertIs<AdSearchResponse>(api)
        assertEquals("111", api.requestId)
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
