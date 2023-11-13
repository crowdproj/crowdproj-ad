package com.crowdproj.ad.biz.repo

import com.crowdproj.ad.biz.CwpAdProcessor
import com.crowdproj.ad.common.CwpAdContext
import com.crowdproj.ad.common.config.CwpAdCorSettings
import com.crowdproj.ad.common.models.*
import com.crowdproj.ad.common.repo.DbAdsResponse
import com.crowdproj.ad.repo.tests.AdRepositoryMock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class BizRepoSearchTest {

    private val userId = CwpAdUserId("321")
    private val command = CwpAdCommand.SEARCH
    private val initAd = CwpAd(
        id = CwpAdId("123"),
        title = "abc",
        description = "abc",
        ownerId = userId,
        adType = CwpAdDealSide.DEMAND,
        visibility = CwpAdVisibility.VISIBLE_PUBLIC,
    )
    private val repo by lazy { AdRepositoryMock(
        invokeSearchAd = {
            DbAdsResponse(
                isSuccess = true,
                data = listOf(initAd),
            )
        }
    ) }
    private val settings by lazy {
        CwpAdCorSettings(
            repoTest = repo
        )
    }
    private val processor by lazy { CwpAdProcessor(settings) }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun repoSearchSuccessTest() = runTest {
        val ctx = CwpAdContext(
            command = command,
            state = CwpAdState.NONE,
            workMode = CwpAdWorkMode.TEST,
            adFilterRequest = CwpAdFilter(
                searchString = "ab",
                dealSide = CwpAdDealSide.DEMAND
            ),
        )
        processor.exec(ctx)
        assertEquals(CwpAdState.FINISHING, ctx.state)
        assertEquals(1, ctx.adsResponse.size)
    }
}
