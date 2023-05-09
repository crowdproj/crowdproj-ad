package com.crowdproj.ad.biz.validation

import com.crowdproj.ad.biz.CwpAdProcessor
import com.crowdproj.ad.common.config.CwpAdCorSettings
import com.crowdproj.ad.common.models.CwpAdCommand
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.otus.otuskotlin.marketplace.backend.repository.inmemory.CwpAdRepoStub
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BizValidationUpdateTest {

    private val command = CwpAdCommand.UPDATE
    private val settings by lazy {
        CwpAdCorSettings(
            repoTest = CwpAdRepoStub()
        )
    }
    private val processor by lazy { CwpAdProcessor(settings) }

    @Test fun correctTitle() = validationTitleCorrect(command, processor)
    @Test fun trimTitle() = validationTitleTrim(command, processor)
    @Test fun emptyTitle() = validationTitleEmpty(command, processor)
    @Test fun badSymbolsTitle() = validationTitleSymbols(command, processor)

    @Test fun correctDescription() = validationDescriptionCorrect(command, processor)
    @Test fun trimDescription() = validationDescriptionTrim(command, processor)
    @Test fun emptyDescription() = validationDescriptionEmpty(command, processor)
    @Test fun badSymbolsDescription() = validationDescriptionSymbols(command, processor)

    @Test fun correctId() = validationIdCorrect(command, processor)
    @Test fun trimId() = validationIdTrim(command, processor)
    @Test fun emptyId() = validationIdEmpty(command, processor)
    @Test fun badFormatId() = validationIdFormat(command, processor)


}

