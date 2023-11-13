package com.crowdproj.ad.common.repo

import com.crowdproj.ad.common.models.CwpAdError

interface IDbResponse<T> {
    val data: T?
    val isSuccess: Boolean
    val errors: List<CwpAdError>
}
