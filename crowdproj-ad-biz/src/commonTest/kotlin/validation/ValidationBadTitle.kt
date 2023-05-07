package com.crowdproj.ad.biz.validation

import com.crowdproj.ad.biz.CwpAdProcessor
import com.crowdproj.ad.common.CwpAdContext
import com.crowdproj.ad.common.models.*
import com.crowdproj.ad.stubs.CwpAdStub
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

private val stub = CwpAdStub.get()

@OptIn(ExperimentalCoroutinesApi::class)
fun validationTitleCorrect(command: CwpAdCommand, processor: CwpAdProcessor) = runTest {
    val ctx = CwpAdContext(
        command = command,
        state = CwpAdState.NONE,
        workMode = CwpAdWorkMode.TEST,
        adRequest = CwpAd(
            id = stub.id,
            title = "abc",
            description = "abc",
            adType = CwpAdDealSide.DEMAND,
            visibility = CwpAdVisibility.VISIBLE_PUBLIC,
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(CwpAdState.FAILING, ctx.state)
    assertEquals("abc", ctx.adValidated.title)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationTitleTrim(command: CwpAdCommand, processor: CwpAdProcessor) = runTest {
    val ctx = CwpAdContext(
        command = command,
        state = CwpAdState.NONE,
        workMode = CwpAdWorkMode.TEST,
        adRequest = CwpAd(
            id = stub.id,
            title = " \n\t abc \t\n ",
            description = "abc",
            adType = CwpAdDealSide.DEMAND,
            visibility = CwpAdVisibility.VISIBLE_PUBLIC,
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(CwpAdState.FAILING, ctx.state)
    assertEquals("abc", ctx.adValidated.title)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationTitleEmpty(command: CwpAdCommand, processor: CwpAdProcessor) = runTest {
    val ctx = CwpAdContext(
        command = command,
        state = CwpAdState.NONE,
        workMode = CwpAdWorkMode.TEST,
        adRequest = CwpAd(
            id = stub.id,
            title = "",
            description = "abc",
            adType = CwpAdDealSide.DEMAND,
            visibility = CwpAdVisibility.VISIBLE_PUBLIC,
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(CwpAdState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("title", error?.field)
    assertContains(error?.message ?: "", "title")
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationTitleSymbols(command: CwpAdCommand, processor: CwpAdProcessor) = runTest {
    val ctx = CwpAdContext(
        command = command,
        state = CwpAdState.NONE,
        workMode = CwpAdWorkMode.TEST,
        adRequest = CwpAd(
            id = CwpAdId("123"),
            title = "!@#$%^&*(),.{}",
            description = "abc",
            adType = CwpAdDealSide.DEMAND,
            visibility = CwpAdVisibility.VISIBLE_PUBLIC,
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(CwpAdState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("title", error?.field)
    assertContains(error?.message ?: "", "title")
}
