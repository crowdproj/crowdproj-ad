package com.crowdproj.ad.biz.validation

import com.crowdproj.ad.common.CwpAdContext
import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.ad.common.helpers.errorValidation
import com.crowdproj.ad.common.helpers.fail

fun ICorAddExecDsl<CwpAdContext>.validateTitleHasContent(title: String) = worker {
    this.title = title
    val regExp = Regex("\\p{L}")
    on { adValidating.title.isNotEmpty() && ! adValidating.title.contains(regExp) }
    handle {
        fail(
            errorValidation(
            field = "title",
            violationCode = "noContent",
            description = "field must contain leters"
        )
        )
    }
}
