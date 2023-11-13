package com.crowdproj.ad.biz.repo

import com.crowdproj.ad.common.CwpAdContext
import com.crowdproj.ad.common.models.CwpAdDealSide
import com.crowdproj.ad.common.models.CwpAdError
import com.crowdproj.ad.common.models.CwpAdState
import com.crowdproj.ad.common.repo.DbAdFilterRequest
import com.crowdproj.ad.common.repo.DbAdsResponse
import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker

fun ICorAddExecDsl<CwpAdContext>.repoOffers(title: String) = worker {
    this.title = title
    description = "Поиск предложений для объявления по названию"
    on { state == CwpAdState.RUNNING }
    handle {
        val adRequest = adRepoPrepare
        val filter = DbAdFilterRequest(
            titleFilter = adRequest.title,
            dealSide = adRequest.adType.opposite(),
        )
        val dbResponse = if (filter.dealSide == CwpAdDealSide.NONE) {
            DbAdsResponse(
                data = null,
                isSuccess = false,
                errors = listOf(
                    CwpAdError(
                        field = "adType",
                        message = "Type of ad must not be empty"
                    )
                )
            )
        } else {
            adRepo.searchAd(filter)
        }

        val resultAds = dbResponse.data
        when {
            !resultAds.isNullOrEmpty() -> adsRepoDone = resultAds.toMutableList()
            dbResponse.isSuccess -> return@handle
            else -> {
                state = CwpAdState.FAILING
                errors.addAll(dbResponse.errors)
            }
        }
    }
}
