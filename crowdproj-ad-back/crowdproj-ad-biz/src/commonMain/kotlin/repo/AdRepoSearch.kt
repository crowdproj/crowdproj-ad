package com.crowdproj.ad.biz.repo

import com.crowdproj.ad.common.CwpAdContext
import com.crowdproj.ad.common.models.CwpAdState
import com.crowdproj.ad.common.repo.DbAdFilterRequest
import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker

fun ICorAddExecDsl<CwpAdContext>.repoSearch(title: String) = worker {
    this.title = title
    description = "Поиск объявлений в БД по фильтру"
    on { state == CwpAdState.RUNNING }
    handle {
        val request = DbAdFilterRequest(
            titleFilter = adFilterValidated.searchString,
            ownerId = adFilterValidated.ownerId,
            dealSide = adFilterValidated.dealSide,
        )
        val result = adRepo.searchAd(request)
        val resultAds = result.data
        if (result.isSuccess && resultAds != null) {
            adsRepoDone = resultAds.toMutableList()
        } else {
            state = CwpAdState.FAILING
            errors.addAll(result.errors)
        }
    }
}
