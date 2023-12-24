package com.crowdproj.ad.backend.repo.gremlin.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Suppress("unused")
@Serializable
enum class ArkadeSerializers {
    @SerialName("record")
    RECORD,
    @SerialName("graph")
    GRAPH,
}
