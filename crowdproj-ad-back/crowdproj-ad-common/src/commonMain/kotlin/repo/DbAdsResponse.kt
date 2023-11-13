package com.crowdproj.ad.common.repo

import com.crowdproj.ad.common.models.CwpAd
import com.crowdproj.ad.common.models.CwpAdError

data class DbAdsResponse(
    override val data: List<CwpAd>?,
    override val isSuccess: Boolean,
    override val errors: List<CwpAdError> = emptyList(),
): IDbResponse<List<CwpAd>> {

    companion object {
        val MOCK_SUCCESS_EMPTY = DbAdsResponse(emptyList(), true)
        fun success(result: List<CwpAd>) = DbAdsResponse(result, true)
        fun error(errors: List<CwpAdError>) = DbAdsResponse(null, false, errors)
        fun error(error: CwpAdError) = DbAdsResponse(null, false, listOf(error))
    }
}
