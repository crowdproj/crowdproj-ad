package com.crowdproj.ad.repo.ydb.model

import com.crowdproj.ad.common.models.*


data class AdEntity(
    val id: String? = null,
    val title: String? = null,
    val description: String? = null,
    val ownerId: String? = null,
    val adType: String? = null,
    val visibility: String? = null,
) {
    constructor(model: CwpAd): this(
        id = model.id.asString().takeIf { it.isNotBlank() },
        title = model.title.takeIf { it.isNotBlank() },
        description = model.description.takeIf { it.isNotBlank() },
        ownerId = model.ownerId.asString().takeIf { it.isNotBlank() },
        adType = model.adType.takeIf { it != CwpAdDealSide.NONE }?.name,
        visibility = model.visibility.takeIf { it != CwpAdVisibility.NONE }?.name,
    )

    fun toInternal() = CwpAd(
        id = id?.let { CwpAdId(it) }?: CwpAdId.NONE,
        title = title?: "",
        description = description?: "",
        ownerId = ownerId?.let { CwpAdUserId(it) }?: CwpAdUserId.NONE,
        adType = adType?.let { CwpAdDealSide.valueOf(it) }?: CwpAdDealSide.NONE,
        visibility = visibility?.let { CwpAdVisibility.valueOf(it) }?: CwpAdVisibility.NONE,
    )
}
