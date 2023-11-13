package com.crowdproj.ad.common.models

enum class CwpAdDealSide {
    NONE,
    DEMAND,
    SUPPLY,
    ;

    fun opposite(): CwpAdDealSide = when (this) {
        DEMAND -> SUPPLY
        SUPPLY -> DEMAND
        NONE -> NONE
    }
}
