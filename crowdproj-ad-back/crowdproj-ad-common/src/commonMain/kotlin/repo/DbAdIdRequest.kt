package com.crowdproj.ad.common.repo

import com.crowdproj.ad.common.models.CwpAd
import com.crowdproj.ad.common.models.CwpAdId
import com.crowdproj.ad.common.models.CwpAdLock

data class DbAdIdRequest(
    val id: CwpAdId,
    val lock: CwpAdLock = CwpAdLock.NONE,
) {
    constructor(ad: CwpAd): this(ad.id, ad.lock)
}
