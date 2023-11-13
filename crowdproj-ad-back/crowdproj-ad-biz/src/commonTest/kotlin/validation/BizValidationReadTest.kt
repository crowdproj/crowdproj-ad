package com.crowdproj.ad.biz.validation

import com.crowdproj.ad.biz.CwpAdProcessor
import com.crowdproj.ad.common.config.CwpAdCorSettings
import com.crowdproj.ad.common.models.CwpAdCommand
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.otus.otuskotlin.marketplace.backend.repository.inmemory.CwpAdRepoStub
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BizValidationReadTest {

    private val command = CwpAdCommand.READ
    private val settings by lazy {
        CwpAdCorSettings(
            repoTest = CwpAdRepoStub()
        )
    }
    private val processor by lazy { CwpAdProcessor(settings) }

    @Test fun correctId() = validationIdCorrect(command, processor)
    @Test fun trimId() = validationIdTrim(command, processor)
    @Test fun emptyId() = validationIdEmpty(command, processor)
    @Test fun badFormatId() = validationIdFormat(command, processor)

}

