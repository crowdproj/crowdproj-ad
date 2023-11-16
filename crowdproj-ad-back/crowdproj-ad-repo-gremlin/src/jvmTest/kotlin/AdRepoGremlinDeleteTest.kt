package com.crowdproj.ad.backend.repo.gremlin

import com.crowdproj.ad.common.models.CwpAd
import com.crowdproj.ad.common.models.CwpAdId
import com.crowdproj.ad.repo.tests.RepoAdDeleteTest

class AdRepoGremlinDeleteTest : RepoAdDeleteTest() {
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
    override val deleteSucc: CwpAd by lazy { repo.initializedObjects[0] }
    override val deleteConc: CwpAd by lazy { repo.initializedObjects[1] }
    override val notFoundId: CwpAdId = CwpAdId("#3100:0")
}
