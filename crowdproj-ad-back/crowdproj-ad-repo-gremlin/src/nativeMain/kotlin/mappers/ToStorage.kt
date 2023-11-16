package com.crowdproj.ad.backend.repo.gremlin.mappers

import com.crowdproj.ad.backend.repo.gremlin.CwpAdGremlinConst.FIELD_AD_TYPE_DEMAND
import com.crowdproj.ad.backend.repo.gremlin.CwpAdGremlinConst.FIELD_AD_TYPE_SUPPLY
import com.crowdproj.ad.backend.repo.gremlin.CwpAdGremlinConst.FIELD_VISIBILITY_GROUP
import com.crowdproj.ad.backend.repo.gremlin.CwpAdGremlinConst.FIELD_VISIBILITY_OWNER
import com.crowdproj.ad.backend.repo.gremlin.CwpAdGremlinConst.FIELD_VISIBILITY_PUBLIC
import com.crowdproj.ad.common.models.CwpAd
import com.crowdproj.ad.common.models.CwpAdDealSide
import com.crowdproj.ad.common.models.CwpAdVisibility
import gremlinRust.CwpAdSt
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.MemScope
import kotlinx.cinterop.cValue
import kotlinx.cinterop.cstr

@OptIn(ExperimentalForeignApi::class)
fun MemScope.adToStore(ad: CwpAd) = cValue<CwpAdSt> {
    id = ad.id.asString().cstr.ptr
    lock = ad.lock.asString().cstr.ptr
    owner_id = ad.ownerId.asString().cstr.ptr
    product_id = ad.productId.asString().cstr.ptr
    title = ad.title.cstr.ptr
    description = ad.description.cstr.ptr
    ad_type = ad.adType.toStore()?.cstr?.ptr
    visibility = ad.visibility.toStore()?.cstr?.ptr
}

private fun CwpAdVisibility.toStore(): String? = when (this) {
    CwpAdVisibility.VISIBLE_PUBLIC -> FIELD_VISIBILITY_PUBLIC
    CwpAdVisibility.VISIBLE_TO_GROUP -> FIELD_VISIBILITY_GROUP
    CwpAdVisibility.VISIBLE_TO_OWNER -> FIELD_VISIBILITY_OWNER
    CwpAdVisibility.NONE -> null
}

private fun CwpAdDealSide.toStore(): String? = when (this) {
    CwpAdDealSide.SUPPLY -> FIELD_AD_TYPE_SUPPLY
    CwpAdDealSide.DEMAND -> FIELD_AD_TYPE_DEMAND
    CwpAdDealSide.NONE -> null
}
