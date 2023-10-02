package com.crowdproj.ad.common

import com.crowdproj.ad.common.config.CwpAdCorSettings
import kotlinx.datetime.Instant
import com.crowdproj.ad.common.models.*
import com.crowdproj.ad.common.stubs.CwpAdStubs
import com.crowdproj.ad.common.repo.IAdRepository

data class CwpAdContext(
    var command: CwpAdCommand = CwpAdCommand.NONE,
    var state: CwpAdState = CwpAdState.NONE,
    val errors: MutableList<CwpAdError> = mutableListOf(),
    var timeStart: Instant = Instant.DISTANT_PAST,
    var settings: CwpAdCorSettings = CwpAdCorSettings.NONE,
    var workMode: CwpAdWorkMode = CwpAdWorkMode.PROD,
    var stubCase: CwpAdStubs = CwpAdStubs.NONE,
    var requestId: CwpAdRequestId = CwpAdRequestId.NONE,
    var adRepo: IAdRepository = IAdRepository.NONE,

    var adRequest: CwpAd = CwpAd(),
    var adFilterRequest: CwpAdFilter = CwpAdFilter(),

    var adValidating: CwpAd = CwpAd(),
    var adFilterValidating: CwpAdFilter = CwpAdFilter(),

    var adValidated: CwpAd = CwpAd(),
    var adFilterValidated: CwpAdFilter = CwpAdFilter(),

    var adRepoRead: CwpAd = CwpAd(),
    var adRepoPrepare: CwpAd = CwpAd(),
    var adRepoDone: CwpAd = CwpAd(),
    var adsRepoDone: MutableList<CwpAd> = mutableListOf(),

    var adResponse: CwpAd = CwpAd(),
    var adsResponse: MutableList<CwpAd> = mutableListOf(),
)
