package com.crowdproj.ad.biz.stubs

import com.crowdproj.ad.common.CwpAdContext
import com.crowdproj.ad.common.models.CwpAdDealSide
import com.crowdproj.ad.common.models.CwpAdState
import com.crowdproj.ad.common.stubs.CwpAdStubs
import com.crowdproj.ad.stubs.CwpAdStub
import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker

fun ICorAddExecDsl<CwpAdContext>.stubSearchSuccess(title: String) = worker {
    this.title = title
    on { stubCase == CwpAdStubs.SUCCESS && state == CwpAdState.RUNNING }
    handle {
        state = CwpAdState.FINISHING
        adsResponse.addAll(CwpAdStub.prepareSearchList(adFilterRequest.searchString, CwpAdDealSide.DEMAND))
    }
}
