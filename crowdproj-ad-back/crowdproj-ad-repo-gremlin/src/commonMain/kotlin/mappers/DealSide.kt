package com.crowdproj.ad.backend.repo.gremlin.mappers

import com.crowdproj.ad.backend.repo.gremlin.CwpAdGremlinConst
import com.crowdproj.ad.backend.repo.gremlin.exceptions.WrongEnumException
import com.crowdproj.ad.common.models.CwpAdDealSide

fun CwpAdDealSide.toDb(): String = when (this) {
    CwpAdDealSide.DEMAND -> CwpAdGremlinConst.FIELD_AD_TYPE_DEMAND
    CwpAdDealSide.SUPPLY -> CwpAdGremlinConst.FIELD_AD_TYPE_SUPPLY
    CwpAdDealSide.NONE -> ""
}

fun String?.dealSideFromDb(): CwpAdDealSide = when (this) {
    CwpAdGremlinConst.FIELD_AD_TYPE_DEMAND -> CwpAdDealSide.DEMAND
    CwpAdGremlinConst.FIELD_AD_TYPE_SUPPLY -> CwpAdDealSide.SUPPLY
    "" -> CwpAdDealSide.NONE
    else -> throw WrongEnumException("Not found value for dealSide in DB: `$this`")
}
