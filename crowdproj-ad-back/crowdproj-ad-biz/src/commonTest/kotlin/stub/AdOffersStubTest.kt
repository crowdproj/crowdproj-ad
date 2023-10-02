package com.crowdproj.ad.biz.stub

import com.crowdproj.ad.biz.CwpAdProcessor
import com.crowdproj.ad.common.CwpAdContext
import com.crowdproj.ad.common.models.*
import com.crowdproj.ad.common.stubs.CwpAdStubs
import com.crowdproj.ad.stubs.CwpAdStub
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.fail

@OptIn(ExperimentalCoroutinesApi::class)
class AdOffersStubTest {

    private val processor = CwpAdProcessor()
    val id = CwpAdId("777")

    @Test
    fun offers() = runTest {

        val ctx = CwpAdContext(
            command = CwpAdCommand.OFFERS,
            state = CwpAdState.NONE,
            workMode = CwpAdWorkMode.STUB,
            stubCase = CwpAdStubs.SUCCESS,
            adRequest = CwpAd(
                id = id,
            ),
        )
        processor.exec(ctx)

        assertEquals(id, ctx.adResponse.id)

        with(CwpAdStub.get()) {
            assertEquals(title, ctx.adResponse.title)
            assertEquals(description, ctx.adResponse.description)
            assertEquals(adType, ctx.adResponse.adType)
            assertEquals(visibility, ctx.adResponse.visibility)
        }

        assertTrue(ctx.adsResponse.size > 1)
        val first = ctx.adsResponse.firstOrNull() ?: fail("Empty response list")
        assertTrue(first.title.contains(ctx.adResponse.title))
        assertTrue(first.description.contains(ctx.adResponse.title))
        assertEquals(CwpAdDealSide.SUPPLY, first.adType)
        assertEquals(CwpAdStub.get().visibility, first.visibility)
    }

    @Test
    fun badId() = runTest {
        val ctx = CwpAdContext(
            command = CwpAdCommand.OFFERS,
            state = CwpAdState.NONE,
            workMode = CwpAdWorkMode.STUB,
            stubCase = CwpAdStubs.BAD_ID,
            adRequest = CwpAd(
                id = id,
            ),
        )
        processor.exec(ctx)
        assertEquals(CwpAd(), ctx.adResponse)
        assertEquals("id", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun databaseError() = runTest {
        val ctx = CwpAdContext(
            command = CwpAdCommand.OFFERS,
            state = CwpAdState.NONE,
            workMode = CwpAdWorkMode.STUB,
            stubCase = CwpAdStubs.DB_ERROR,
            adRequest = CwpAd(
                id = id,
            ),
        )
        processor.exec(ctx)
        assertEquals(CwpAd(), ctx.adResponse)
        assertEquals("internal", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badNoCase() = runTest {
        val ctx = CwpAdContext(
            command = CwpAdCommand.OFFERS,
            state = CwpAdState.NONE,
            workMode = CwpAdWorkMode.STUB,
            stubCase = CwpAdStubs.BAD_TITLE,
            adRequest = CwpAd(
                id = id,
            ),
        )
        processor.exec(ctx)
        assertEquals(CwpAd(), ctx.adResponse)
        assertEquals("stub", ctx.errors.firstOrNull()?.field)
    }
}
