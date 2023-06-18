package com.crowdproj.ad.repo.tests

import com.crowdproj.ad.common.models.*


abstract class BaseInitAds(val op: String): IInitObjects<CwpAd> {

    open val lockOld: CwpAdLock = CwpAdLock("20000000-0000-0000-0000-000000000001")
    open val lockBad: CwpAdLock = CwpAdLock("20000000-0000-0000-0000-000000000009")

    fun createInitTestModel(
        suf: String,
        ownerId: CwpAdUserId = CwpAdUserId("owner-123"),
        adType: CwpAdDealSide = CwpAdDealSide.DEMAND,
        lock: CwpAdLock = lockOld,
    ) = CwpAd(
        id = CwpAdId("ad-repo-$op-$suf"),
        title = "$suf stub",
        description = "$suf stub description",
        ownerId = ownerId,
        visibility = CwpAdVisibility.VISIBLE_TO_OWNER,
        adType = adType,
        lock = lock,
    )
}
