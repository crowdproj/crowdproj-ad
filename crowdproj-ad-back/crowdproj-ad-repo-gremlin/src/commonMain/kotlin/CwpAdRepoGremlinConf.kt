package com.crowdproj.ad.backend.repo.gremlin

import com.benasher44.uuid.uuid4
import com.crowdproj.ad.common.models.CwpAd

data class CwpAdRepoGremlinConf(
    val hosts: String,
    val port: Int = 8182,
    val database: String = "graph",
    val enableSsl: Boolean = false,
    val user: String = "root",
    val pass: String = "",
    val initObjects: Collection<CwpAd> = emptyList(),
    val randomUuid: () -> String = { uuid4().toString() },
    val mustClean: Boolean = false,
)
