package com.crowdproj.ad.backend.repository.gremlin

import com.crowdproj.ad.common.models.CwpAd
import com.crowdproj.ad.repo.tests.RepoAdSearchTest

class AdRepoGremlinSearchTest : RepoAdSearchTest() {
    override val repo: CwpAdRepoGremlin by lazy {
        CwpAdRepoGremlin(
            CwpAdRepoGremlinConf(
                hosts = ArcadeDbContainer.container.host,
                port = ArcadeDbContainer.container.getMappedPort(8182),
                enableSsl = false,
                user = ArcadeDbContainer.username,
                pass = ArcadeDbContainer.password,
                initObjects = initObjects,
                mustClean = true,
            )
        )
    }

    override val initializedObjects: List<CwpAd> by lazy {
        repo.initializedObjects
    }
}
