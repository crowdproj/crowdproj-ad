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

class BizRepoReadTest {

    private val userId = CwpAdUserId("321")
    private val command = CwpAdCommand.READ
    private val initAd = CwpAd(
        id = CwpAdId("123"),
        title = "abc",
        description = "abc",
        ownerId = userId,
        adType = CwpAdDealSide.DEMAND,
        visibility = CwpAdVisibility.VISIBLE_PUBLIC,
    )
    private val repo by lazy { AdRepositoryMock(
        invokeReadAd = {
            DbAdResponse(
                isSuccess = true,
                data = initAd,
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
    fun repoReadSuccessTest() = runTest {
        val ctx = CwpAdContext(
            command = command,
            state = CwpAdState.NONE,
            workMode = CwpAdWorkMode.TEST,
            adRequest = CwpAd(
                id = CwpAdId("123"),
            ),
        )
        processor.exec(ctx)
        assertEquals(CwpAdState.FINISHING, ctx.state)
        assertEquals(initAd.id, ctx.adResponse.id)
        assertEquals(initAd.title, ctx.adResponse.title)
        assertEquals(initAd.description, ctx.adResponse.description)
        assertEquals(initAd.adType, ctx.adResponse.adType)
        assertEquals(initAd.visibility, ctx.adResponse.visibility)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun repoReadNotFoundTest() = repoNotFoundTest(command)
}
