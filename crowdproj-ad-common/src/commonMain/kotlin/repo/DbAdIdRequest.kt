package com.crowdproj.ad.common.repo

import com.crowdproj.ad.common.models.CwpAd
import com.crowdproj.ad.common.models.CwpAdId

data class DbAdIdRequest(
    val id: CwpAdId,
) {
    constructor(ad: CwpAd): this(ad.id)
}
