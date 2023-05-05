package com.crowdproj.ad.common

import kotlinx.datetime.Instant
import com.crowdproj.ad.common.models.*
import com.crowdproj.ad.common.stubs.CwpStubs

data class CwpAdContext(
    var command: CwpAdCommand = CwpAdCommand.NONE,
    var state: CwpAdState = CwpAdState.NONE,
    val errors: MutableList<CwpAdError> = mutableListOf(),
    var timeStart: Instant = Instant.DISTANT_PAST,

    var workMode: CwpAdWorkMode = CwpAdWorkMode.PROD,
    var stubCase: CwpStubs = CwpStubs.NONE,

    var requestId: CwpAdRequestId = CwpAdRequestId.NONE,
    var adRequest: CwpAd = CwpAd(),
    var adFilterRequest: CwpAdFilter = CwpAdFilter(),
    var adResponse: CwpAd = CwpAd(),
    var adsResponse: MutableList<CwpAd> = mutableListOf(),
)
