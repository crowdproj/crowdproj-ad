package com.crowdproj.ad.backend.repo.gremlin.models

import kotlinx.serialization.Serializable

@Serializable
data class ArkadeResultStr(
    override val user: String?,
    override val version: String?,
    override val serverName: String?,
    override val result: String?,
    override val error: String?,
    override val detail: String?,
    override val exception: String?,
): IArkadeResult<String>
