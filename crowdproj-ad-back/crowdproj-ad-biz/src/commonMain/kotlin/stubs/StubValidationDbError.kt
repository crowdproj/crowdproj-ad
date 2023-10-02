package com.crowdproj.ad.biz.stubs

import com.crowdproj.ad.common.CwpAdContext
import com.crowdproj.ad.common.models.CwpAdError
import com.crowdproj.ad.common.models.CwpAdState
import com.crowdproj.ad.common.stubs.CwpAdStubs
import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker

fun ICorAddExecDsl<CwpAdContext>.stubDbError(title: String) = worker {
    this.title = title
    on { stubCase == CwpAdStubs.DB_ERROR && state == CwpAdState.RUNNING }
    handle {
        state = CwpAdState.FAILING
        this.errors.add(
            CwpAdError(
                group = "internal",
                code = "internal-db",
                message = "Internal error"
            )
        )
    }
}
