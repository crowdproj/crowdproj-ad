package com.crowdproj.ad.repo.tests

import com.crowdproj.ad.common.models.*


abstract class BaseInitAds(val op: String): IInitObjects<CwpAd> {

    fun createInitTestModel(
        suf: String,
        ownerId: CwpAdUserId = CwpAdUserId("owner-123"),
        adType: CwpAdDealSide = CwpAdDealSide.DEMAND,
    ) = CwpAd(
        id = CwpAdId("ad-repo-$op-$suf"),
        title = "$suf stub",
        description = "$suf stub description",
        ownerId = ownerId,
        visibility = CwpAdVisibility.VISIBLE_TO_OWNER,
        adType = adType,
    )
}
