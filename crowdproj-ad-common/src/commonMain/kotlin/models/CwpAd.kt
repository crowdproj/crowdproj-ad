package com.crowdproj.ad.common.models

data class CwpAd(
    var id: CwpAdId = CwpAdId.NONE,
    var title: String = "",
    var description: String = "",
    var ownerId: CwpAdUserId = CwpAdUserId.NONE,
    val adType: CwpAdDealSide = CwpAdDealSide.NONE,
    var visibility: CwpAdVisibility = CwpAdVisibility.NONE,
    var productId: CwpAdProductId = CwpAdProductId.NONE,
    val permissionsClient: MutableSet<CwpAdPermissionClient> = mutableSetOf()
)
