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
class AdSearchStubTest {

    private val processor = CwpAdProcessor()
    val filter = CwpAdFilter(searchString = "bolt")

    @Test
    fun read() = runTest {

        val ctx = CwpAdContext(
            command = CwpAdCommand.SEARCH,
            state = CwpAdState.NONE,
            workMode = CwpAdWorkMode.STUB,
            stubCase = CwpAdStubs.SUCCESS,
            adFilterRequest = filter,
        )
        processor.exec(ctx)
        assertTrue(ctx.adsResponse.size > 1)
        val first = ctx.adsResponse.firstOrNull() ?: fail("Empty response list")
        assertTrue(first.title.contains(filter.searchString))
        assertTrue(first.description.contains(filter.searchString))
        with (CwpAdStub.get()) {
            assertEquals(adType, first.adType)
            assertEquals(visibility, first.visibility)
        }
    }

    @Test
    fun badId() = runTest {
        val ctx = CwpAdContext(
            command = CwpAdCommand.SEARCH,
            state = CwpAdState.NONE,
            workMode = CwpAdWorkMode.STUB,
            stubCase = CwpAdStubs.BAD_ID,
            adFilterRequest = filter,
        )
        processor.exec(ctx)
        assertEquals(CwpAd(), ctx.adResponse)
        assertEquals("id", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun databaseError() = runTest {
        val ctx = CwpAdContext(
            command = CwpAdCommand.SEARCH,
            state = CwpAdState.NONE,
            workMode = CwpAdWorkMode.STUB,
            stubCase = CwpAdStubs.DB_ERROR,
            adFilterRequest = filter,
        )
        processor.exec(ctx)
        assertEquals(CwpAd(), ctx.adResponse)
        assertEquals("internal", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badNoCase() = runTest {
        val ctx = CwpAdContext(
            command = CwpAdCommand.SEARCH,
            state = CwpAdState.NONE,
            workMode = CwpAdWorkMode.STUB,
            stubCase = CwpAdStubs.BAD_TITLE,
            adFilterRequest = filter,
        )
        processor.exec(ctx)
        assertEquals(CwpAd(), ctx.adResponse)
        assertEquals("stub", ctx.errors.firstOrNull()?.field)
    }
}
