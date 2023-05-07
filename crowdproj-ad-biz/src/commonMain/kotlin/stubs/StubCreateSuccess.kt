package com.crowdproj.ad.biz.stubs

import com.crowdproj.ad.common.CwpAdContext
import com.crowdproj.ad.common.models.CwpAdDealSide
import com.crowdproj.ad.common.models.CwpAdState
import com.crowdproj.ad.common.models.CwpAdVisibility
import com.crowdproj.ad.common.stubs.CwpAdStubs
import com.crowdproj.ad.stubs.CwpAdStub
import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker

fun ICorAddExecDsl<CwpAdContext>.stubCreateSuccess(title: String) = worker {
    this.title = title
    on { stubCase == CwpAdStubs.SUCCESS && state == CwpAdState.RUNNING }
    handle {
        state = CwpAdState.FINISHING
        val stub = CwpAdStub.prepareResult {
            adRequest.title.takeIf { it.isNotBlank() }?.also { this.title = it }
            adRequest.description.takeIf { it.isNotBlank() }?.also { this.description = it }
            adRequest.adType.takeIf { it != CwpAdDealSide.NONE }?.also { this.adType = it }
            adRequest.visibility.takeIf { it != CwpAdVisibility.NONE }?.also { this.visibility = it }
        }
        adResponse = stub
    }
}
