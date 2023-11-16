package com.crowdproj.ad.backend.repo.gremlin

import com.crowdproj.ad.common.models.CwpAd
import com.crowdproj.ad.repo.tests.RepoAdReadTest

class AdRepoGremlinReadTest : RepoAdReadTest() {
    override val repo: CwpAdRepoGremlin by lazy {
        CwpAdRepoGremlin(
            CwpAdRepoGremlinConf(
                hosts = ArcadeDbContainer.container.host,
                port = ArcadeDbContainer.container.getMappedPort(8182),
                user = ArcadeDbContainer.username,
                pass = ArcadeDbContainer.password,
                enableSsl = false,
                initObjects = initObjects,
                mustClean = true,
            )
        )
    }
    override val readSucc: CwpAd by lazy { repo.initializedObjects[0] }
}
