package com.crowdproj.ad.backend.repo.gremlin.models

import kotlinx.serialization.Serializable

@Serializable
sealed interface IArkadeResult<T> {
    val user: String?
    val version: String?
    val serverName: String?
    val error: String?
    val detail: String?
    val exception: String?
    val result: T?
}
