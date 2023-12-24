package com.crowdproj.ad.backend.repo.gremlin.models

import kotlinx.serialization.Serializable


@Serializable
data class ArkadeCommandQuery(
    val language: String = "gremlin",
    val command: String,
    val params: Map<String, String?> = emptyMap(),
    val limit: Int? = null,
    val serializer: ArkadeSerializers? = null,
)
