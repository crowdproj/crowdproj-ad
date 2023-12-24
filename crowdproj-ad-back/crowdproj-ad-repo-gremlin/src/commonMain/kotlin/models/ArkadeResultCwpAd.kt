package com.crowdproj.ad.backend.repo.gremlin.models

import com.crowdproj.ad.backend.repo.gremlin.CwpAdGremlinConst
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArkadeResultCwpAd(
    @SerialName(CwpAdGremlinConst.FIELD_ID)
    val id: String?,
//    @SerialName("@type")
//    val type: String?,
//    @SerialName("@cat")
//    val cat: String?,
    val title: String?,
    val description: String?,
    val lock: String?,
    val ownerId: String?,
    val adType: String?,
    val visibility: String?,
    val productId: String?,
    @SerialName(CwpAdGremlinConst.FIELD_TMP_RESULT)
    val mark: String?,
)
