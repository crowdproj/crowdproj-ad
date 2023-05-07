package com.crowdproj.ad.biz.stubs

import com.crowdproj.ad.common.CwpAdContext
import com.crowdproj.ad.common.models.CwpAdError
import com.crowdproj.ad.common.models.CwpAdState
import com.crowdproj.ad.common.stubs.CwpAdStubs
import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker

fun ICorAddExecDsl<CwpAdContext>.stubValidationBadDescription(title: String) = worker {
    this.title = title
    on { stubCase == CwpAdStubs.BAD_DESCRIPTION && state == CwpAdState.RUNNING }
    handle {
        state = CwpAdState.FAILING
        this.errors.add(
            CwpAdError(
                group = "validation",
                code = "validation-description",
                field = "description",
                message = "Wrong description field"
            )
        )
    }
}
