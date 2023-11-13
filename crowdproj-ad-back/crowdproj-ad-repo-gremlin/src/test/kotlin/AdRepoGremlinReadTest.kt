package ru.otus.otuskotlin.marketplace.backend.repository.gremlin

import com.crowdproj.ad.common.models.CwpAd
import com.crowdproj.ad.repo.tests.RepoAdReadTest

class AdRepoGremlinReadTest : RepoAdReadTest() {
    override val repo: CwpAdRepoGremlin by lazy {
        CwpAdRepoGremlin(
            hosts = ArcadeDbContainer.container.host,
            port = ArcadeDbContainer.container.getMappedPort(8182),
            user = ArcadeDbContainer.username,
            pass = ArcadeDbContainer.password,
            enableSsl = false,
            initObjects = initObjects,
            initRepo = { g -> g.V().drop().iterate() },
        )
    }
    override val readSucc: CwpAd by lazy { repo.initializedObjects[0] }
}
