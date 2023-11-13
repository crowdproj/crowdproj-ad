package com.crowdproj.ad.biz.repo

import com.crowdproj.ad.common.CwpAdContext
import com.crowdproj.ad.common.models.CwpAdState
import com.crowdproj.ad.common.repo.DbAdRequest
import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker

fun ICorAddExecDsl<CwpAdContext>.repoUpdate(title: String) = worker {
    this.title = title
    on { state == CwpAdState.RUNNING }
    handle {
        val request = DbAdRequest(adRepoPrepare)
        val result = adRepo.updateAd(request)
        val resultAd = result.data
        if (result.isSuccess && resultAd != null) {
            adRepoDone = resultAd
        } else {
            state = CwpAdState.FAILING
            errors.addAll(result.errors)
            adRepoDone
        }
    }
}
