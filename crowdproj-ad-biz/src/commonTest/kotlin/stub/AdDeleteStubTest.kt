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

@OptIn(ExperimentalCoroutinesApi::class)
class AdDeleteStubTest {

    private val processor = CwpAdProcessor()
    val id = CwpAdId("666")

    @Test
    fun delete() = runTest {

        val ctx = CwpAdContext(
            command = CwpAdCommand.DELETE,
            state = CwpAdState.NONE,
            workMode = CwpAdWorkMode.STUB,
            stubCase = CwpAdStubs.SUCCESS,
            adRequest = CwpAd(
                id = id,
            ),
        )
        processor.exec(ctx)

        val stub = CwpAdStub.get()
        assertEquals(stub.id, ctx.adResponse.id)
        assertEquals(stub.title, ctx.adResponse.title)
        assertEquals(stub.description, ctx.adResponse.description)
        assertEquals(stub.adType, ctx.adResponse.adType)
        assertEquals(stub.visibility, ctx.adResponse.visibility)
    }

    @Test
    fun badId() = runTest {
        val ctx = CwpAdContext(
            command = CwpAdCommand.DELETE,
            state = CwpAdState.NONE,
            workMode = CwpAdWorkMode.STUB,
            stubCase = CwpAdStubs.BAD_ID,
            adRequest = CwpAd(),
        )
        processor.exec(ctx)
        assertEquals(CwpAd(), ctx.adResponse)
        assertEquals("id", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun databaseError() = runTest {
        val ctx = CwpAdContext(
            command = CwpAdCommand.DELETE,
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
            command = CwpAdCommand.DELETE,
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
