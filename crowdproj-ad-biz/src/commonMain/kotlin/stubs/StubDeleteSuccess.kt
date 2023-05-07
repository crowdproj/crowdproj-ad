package com.crowdproj.ad.biz.stubs

import com.crowdproj.ad.common.CwpAdContext
import com.crowdproj.ad.common.models.CwpAdState
import com.crowdproj.ad.common.stubs.CwpAdStubs
import com.crowdproj.ad.stubs.CwpAdStub
import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker

fun ICorAddExecDsl<CwpAdContext>.stubDeleteSuccess(title: String) = worker {
    this.title = title
    on { stubCase == CwpAdStubs.SUCCESS && state == CwpAdState.RUNNING }
    handle {
        state = CwpAdState.FINISHING
        val stub = CwpAdStub.prepareResult {
            adRequest.title.takeIf { it.isNotBlank() }?.also { this.title = it }
        }
        adResponse = stub
    }
}
