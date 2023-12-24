package com.crowdproj.ad.backend.repo.gremlin

import com.crowdproj.ad.common.models.CwpAd
import com.crowdproj.ad.repo.tests.RepoAdSearchTest
import kotlin.random.Random
import kotlin.random.nextUInt

class AdRepoGremlinSearchTest : RepoAdSearchTest() {
    override val repo: CwpAdRepoGremlin by lazy {
        CwpAdRepoGremlin(
            CwpAdRepoGremlinConf(
                hosts = ArcadeDbContainer.container.host,
                port = ArcadeDbContainer.container.getMappedPort(2480),
                enableSsl = false,
                user = ArcadeDbContainer.username,
                pass = ArcadeDbContainer.password,
                database = "search_${Random.Default.nextUInt(1_000_000u)}",
                initObjects = initObjects,
                mustClean = true,
            )
        )
    }

    override val initializedObjects: List<CwpAd> by lazy {
        repo.initializedObjects
    }
}
