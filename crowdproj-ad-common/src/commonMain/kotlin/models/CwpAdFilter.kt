package ru.otus.otuskotlin.marketplace.common.models

data class CwpAdFilter(
    var searchString: String = "",
    var ownerId: CwpAdUserId = CwpAdUserId.NONE,
    var dealSide: CwpAdDealSide = CwpAdDealSide.NONE,
)
