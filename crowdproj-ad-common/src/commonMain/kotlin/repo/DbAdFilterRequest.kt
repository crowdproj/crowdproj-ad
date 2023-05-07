package com.crowdproj.ad.common.repo

import com.crowdproj.ad.common.models.CwpAdDealSide
import com.crowdproj.ad.common.models.CwpAdUserId

data class DbAdFilterRequest(
    val titleFilter: String = "",
    val ownerId: CwpAdUserId = CwpAdUserId.NONE,
    val dealSide: CwpAdDealSide = CwpAdDealSide.NONE,
)
