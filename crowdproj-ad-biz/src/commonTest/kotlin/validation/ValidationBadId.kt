package com.crowdproj.ad.biz.validation

import com.crowdproj.ad.biz.CwpAdProcessor
import com.crowdproj.ad.common.CwpAdContext
import com.crowdproj.ad.common.models.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

@OptIn(ExperimentalCoroutinesApi::class)
fun validationIdCorrect(command: CwpAdCommand, processor: CwpAdProcessor) = runTest {
    val ctx = CwpAdContext(
        command = command,
        state = CwpAdState.NONE,
        workMode = CwpAdWorkMode.TEST,
        adRequest = CwpAd(
            id = CwpAdId("123-234-abc-ABC"),
            title = "abc",
            description = "abc",
            adType = CwpAdDealSide.DEMAND,
            visibility = CwpAdVisibility.VISIBLE_PUBLIC,
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(CwpAdState.FAILING, ctx.state)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationIdTrim(command: CwpAdCommand, processor: CwpAdProcessor) = runTest {
    val ctx = CwpAdContext(
        command = command,
        state = CwpAdState.NONE,
        workMode = CwpAdWorkMode.TEST,
        adRequest = CwpAd(
            id = CwpAdId(" \n\t 123-234-abc-ABC \n\t "),
            title = "abc",
            description = "abc",
            adType = CwpAdDealSide.DEMAND,
            visibility = CwpAdVisibility.VISIBLE_PUBLIC,
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(CwpAdState.FAILING, ctx.state)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationIdEmpty(command: CwpAdCommand, processor: CwpAdProcessor) = runTest {
    val ctx = CwpAdContext(
        command = command,
        state = CwpAdState.NONE,
        workMode = CwpAdWorkMode.TEST,
        adRequest = CwpAd(
            id = CwpAdId(""),
            title = "abc",
            description = "abc",
            adType = CwpAdDealSide.DEMAND,
            visibility = CwpAdVisibility.VISIBLE_PUBLIC,
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(CwpAdState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("id", error?.field)
    assertContains(error?.message ?: "", "id")
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationIdFormat(command: CwpAdCommand, processor: CwpAdProcessor) = runTest {
    val ctx = CwpAdContext(
        command = command,
        state = CwpAdState.NONE,
        workMode = CwpAdWorkMode.TEST,
        adRequest = CwpAd(
            id = CwpAdId("!@#\$%^&*(),.{}"),
            title = "abc",
            description = "abc",
            adType = CwpAdDealSide.DEMAND,
            visibility = CwpAdVisibility.VISIBLE_PUBLIC,
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(CwpAdState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("id", error?.field)
    assertContains(error?.message ?: "", "id")
}
