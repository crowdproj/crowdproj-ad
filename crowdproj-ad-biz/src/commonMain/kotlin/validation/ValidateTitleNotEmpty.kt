package com.crowdproj.ad.biz.validation

import com.crowdproj.ad.common.CwpAdContext
import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.ad.common.helpers.errorValidation
import com.crowdproj.ad.common.helpers.fail

fun ICorAddExecDsl<CwpAdContext>.validateTitleNotEmpty(title: String) = worker {
    this.title = title
    on { adValidating.title.isEmpty() }
    handle {
        fail(
            errorValidation(
            field = "title",
            violationCode = "empty",
            description = "field must not be empty"
        )
        )
    }
}
