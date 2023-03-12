package ru.otus.otuskotlin.marketplace.common

import kotlinx.datetime.Instant
import ru.otus.otuskotlin.marketplace.common.models.*
import ru.otus.otuskotlin.marketplace.common.stubs.CwpStubs

data class CwpAdContext(
    var command: CwpCommand = CwpCommand.NONE,
    var state: CwpState = CwpState.NONE,
    val errors: MutableList<CwpError> = mutableListOf(),

    var workMode: CwpAdWorkMode = CwpAdWorkMode.PROD,
    var stubCase: CwpStubs = CwpStubs.NONE,

    var requestId: CwpRequestId = CwpRequestId.NONE,
    var timeStart: Instant = Instant.NONE,
    var adRequest: CwpAd = CwpAd(),
    var adFilterRequest: CwpAdFilter = CwpAdFilter(),
    var adResponse: CwpAd = CwpAd(),
    var adsResponse: MutableList<CwpAd> = mutableListOf(),
)
