package ru.otus.otuskotlin.marketplace.backend.repository.gremlin

import com.crowdproj.ad.common.models.CwpAd
import com.crowdproj.ad.repo.tests.RepoAdUpdateTest


class AdRepoGremlinUpdateTest: RepoAdUpdateTest() {
    override val repo: CwpAdRepoGremlin by lazy {
        CwpAdRepoGremlin(
            hosts = ArcadeDbContainer.container.host,
            port = ArcadeDbContainer.container.getMappedPort(8182),
            enableSsl = false,
            user = ArcadeDbContainer.username,
            pass = ArcadeDbContainer.password,
            initObjects = initObjects,
            initRepo = { g -> g.V().drop().iterate() },
            randomUuid = { lockNew.asString() },
        )
    }
    override val updateSucc: CwpAd by lazy { repo.initializedObjects[0] }
    override val updateConc: CwpAd by lazy { repo.initializedObjects[1] }
}
