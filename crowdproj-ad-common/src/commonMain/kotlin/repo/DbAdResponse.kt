package com.crowdproj.ad.common.repo

import com.crowdproj.ad.common.models.CwpAd
import com.crowdproj.ad.common.models.CwpAdError

data class DbAdResponse(
    override val data: CwpAd?,
    override val isSuccess: Boolean,
    override val errors: List<CwpAdError> = emptyList()
): IDbResponse<CwpAd> {

    companion object {
        val MOCK_SUCCESS_EMPTY = DbAdResponse(null, true)
        fun success(result: CwpAd) = DbAdResponse(result, true)
        fun error(errors: List<CwpAdError>) = DbAdResponse(null, false, errors)
        fun error(error: CwpAdError) = DbAdResponse(null, false, listOf(error))
    }
}
