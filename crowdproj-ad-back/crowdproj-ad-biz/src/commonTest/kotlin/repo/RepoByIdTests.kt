package com.crowdproj.ad.biz.repo

import com.crowdproj.ad.biz.CwpAdProcessor
import com.crowdproj.ad.common.CwpAdContext
import com.crowdproj.ad.common.config.CwpAdCorSettings
import com.crowdproj.ad.common.models.*
import com.crowdproj.ad.common.repo.DbAdResponse
import com.crowdproj.ad.repo.tests.AdRepositoryMock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.assertEquals

private val initAd = CwpAd(
    id = CwpAdId("123"),
    title = "abc",
    description = "abc",
    adType = CwpAdDealSide.DEMAND,
    visibility = CwpAdVisibility.VISIBLE_PUBLIC,
)
private val repo = AdRepositoryMock(
        invokeReadAd = {
            if (it.id == initAd.id) {
                DbAdResponse(
                    isSuccess = true,
                    data = initAd,
                )
            } else DbAdResponse(
                isSuccess = false,
                data = null,
                errors = listOf(CwpAdError(message = "Not found", field = "id"))
            )
        }
    )
private val settings by lazy {
    CwpAdCorSettings(
        repoTest = repo
    )
}
private val processor by lazy { CwpAdProcessor(settings) }

@OptIn(ExperimentalCoroutinesApi::class)
fun repoNotFoundTest(command: CwpAdCommand) = runTest {
    val ctx = CwpAdContext(
        command = command,
        state = CwpAdState.NONE,
        workMode = CwpAdWorkMode.TEST,
        adRequest = CwpAd(
            id = CwpAdId("12345"),
            title = "xyz",
            description = "xyz",
            adType = CwpAdDealSide.DEMAND,
            visibility = CwpAdVisibility.VISIBLE_TO_GROUP,
        ),
    )
    processor.exec(ctx)
    assertEquals(CwpAdState.FAILING, ctx.state)
    assertEquals(CwpAd(), ctx.adResponse)
    assertEquals(1, ctx.errors.size)
    assertEquals("id", ctx.errors.first().field)
}
