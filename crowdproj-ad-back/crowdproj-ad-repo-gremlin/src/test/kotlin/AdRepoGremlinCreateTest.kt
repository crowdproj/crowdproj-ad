package ru.otus.otuskotlin.marketplace.backend.repository.gremlin

import com.crowdproj.ad.common.repo.IAdRepository
import com.crowdproj.ad.repo.tests.RepoAdCreateTest
import com.crowdproj.ad.repo.tests.RepoAdSearchTest


class AdRepoGremlinCreateTest : RepoAdCreateTest() {
    override val repo: IAdRepository by lazy {
        CwpAdRepoGremlin(
            hosts = ArcadeDbContainer.container.host,
            port = ArcadeDbContainer.container.getMappedPort(8182),
            enableSsl = false,
            user = ArcadeDbContainer.username,
            pass = ArcadeDbContainer.password,
            initObjects = RepoAdSearchTest.initObjects,
            initRepo = { g -> g.V().drop().iterate() },
            randomUuid = { lockNew.asString() }
        )
    }
}
