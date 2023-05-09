package com.crowdproj.ad.biz.repo

import com.crowdproj.ad.biz.CwpAdProcessor
import com.crowdproj.ad.common.CwpAdContext
import com.crowdproj.ad.common.config.CwpAdCorSettings
import com.crowdproj.ad.common.models.*
import com.crowdproj.ad.common.repo.DbAdResponse
import com.crowdproj.ad.repo.tests.AdRepositoryMock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class BizRepoCreateTest {

    private val userId = CwpAdUserId("321")
    private val command = CwpAdCommand.CREATE
    private val uuid = "10000000-0000-0000-0000-000000000001"
    private val repo = AdRepositoryMock(
        invokeCreateAd = {
            DbAdResponse(
                isSuccess = true,
                data = CwpAd(
                    id = CwpAdId(uuid),
                    title = it.ad.title,
                    description = it.ad.description,
                    ownerId = userId,
                    adType = it.ad.adType,
                    visibility = it.ad.visibility,
                )
            )
        }
    )
    private val settings = CwpAdCorSettings(
        repoTest = repo
    )
    private val processor = CwpAdProcessor(settings)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun repoCreateSuccessTest() = runTest {
        val ctx = CwpAdContext(
            command = command,
            state = CwpAdState.NONE,
            workMode = CwpAdWorkMode.TEST,
            adRequest = CwpAd(
                title = "abc",
                description = "abc",
                adType = CwpAdDealSide.DEMAND,
                visibility = CwpAdVisibility.VISIBLE_PUBLIC,
            ),
        )
        processor.exec(ctx)
        assertEquals(CwpAdState.FINISHING, ctx.state)
        assertNotEquals(CwpAdId.NONE, ctx.adResponse.id)
        assertEquals("abc", ctx.adResponse.title)
        assertEquals("abc", ctx.adResponse.description)
        assertEquals(CwpAdDealSide.DEMAND, ctx.adResponse.adType)
        assertEquals(CwpAdVisibility.VISIBLE_PUBLIC, ctx.adResponse.visibility)
    }
}
