package com.crowdproj.ad.biz.validation

import com.crowdproj.ad.biz.CwpAdProcessor
import com.crowdproj.ad.common.CwpAdContext
import com.crowdproj.ad.common.config.CwpAdCorSettings
import com.crowdproj.ad.common.models.CwpAdCommand
import com.crowdproj.ad.common.models.CwpAdFilter
import com.crowdproj.ad.common.models.CwpAdState
import com.crowdproj.ad.common.models.CwpAdWorkMode
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import ru.otus.otuskotlin.marketplace.backend.repository.inmemory.CwpAdRepoStub
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

@OptIn(ExperimentalCoroutinesApi::class)
class BizValidationSearchTest {

    private val command = CwpAdCommand.SEARCH
    private val settings by lazy {
        CwpAdCorSettings(
            repoTest = CwpAdRepoStub()
        )
    }
    private val processor by lazy { CwpAdProcessor(settings) }

    @Test
    fun correctEmpty() = runTest {
        val ctx = CwpAdContext(
            command = command,
            state = CwpAdState.NONE,
            workMode = CwpAdWorkMode.TEST,
            adFilterRequest = CwpAdFilter()
        )
        processor.exec(ctx)
        assertEquals(0, ctx.errors.size)
        assertNotEquals(CwpAdState.FAILING, ctx.state)
    }
}

