package com.crowdproj.ad.backend.repo.gremlin

import com.crowdproj.ad.common.models.CwpAd
import com.crowdproj.ad.repo.tests.RepoAdUpdateTest

class AdRepoGremlinUpdateTest : RepoAdUpdateTest() {
    override val repo: CwpAdRepoGremlin by lazy {
        CwpAdRepoGremlin(
            CwpAdRepoGremlinConf(
                hosts = ArcadeDbContainer.container.host,
                port = ArcadeDbContainer.container.getMappedPort(8182),
                enableSsl = false,
                user = ArcadeDbContainer.username,
                pass = ArcadeDbContainer.password,
                initObjects = initObjects,
                randomUuid = { lockNew.asString() },
                mustClean = true,
            )
        )
    }
    override val updateSucc: CwpAd by lazy { repo.initializedObjects[0] }
    override val updateConc: CwpAd by lazy { repo.initializedObjects[1] }
}
