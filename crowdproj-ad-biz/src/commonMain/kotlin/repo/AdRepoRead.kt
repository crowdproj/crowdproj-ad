package com.crowdproj.ad.biz.repo

import com.crowdproj.ad.common.CwpAdContext
import com.crowdproj.ad.common.models.CwpAdState
import com.crowdproj.ad.common.repo.DbAdIdRequest
import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker

fun ICorAddExecDsl<CwpAdContext>.repoRead(title: String) = worker {
    this.title = title
    description = "Чтение объявления из БД"
    on { state == CwpAdState.RUNNING }
    handle {
        val request = DbAdIdRequest(adValidated)
        val result = adRepo.readAd(request)
        val resultAd = result.data
        if (result.isSuccess && resultAd != null) {
            adRepoRead = resultAd
        } else {
            state = CwpAdState.FAILING
            errors.addAll(result.errors)
        }
    }
}
