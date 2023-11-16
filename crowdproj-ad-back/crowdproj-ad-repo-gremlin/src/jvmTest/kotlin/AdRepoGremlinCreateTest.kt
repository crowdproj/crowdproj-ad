package com.crowdproj.ad.backend.repo.gremlin

import com.crowdproj.ad.common.repo.IAdRepository
import com.crowdproj.ad.repo.tests.RepoAdCreateTest
import com.crowdproj.ad.repo.tests.RepoAdSearchTest

class AdRepoGremlinCreateTest : RepoAdCreateTest() {
    override val repo: IAdRepository by lazy {
        CwpAdRepoGremlin(
            CwpAdRepoGremlinConf(
                hosts = ArcadeDbContainer.container.host,
                port = ArcadeDbContainer.container.getMappedPort(8182),
                enableSsl = false,
                user = ArcadeDbContainer.username,
                pass = ArcadeDbContainer.password,
                initObjects = RepoAdSearchTest.initObjects,
                randomUuid = { lockNew.asString() },
                mustClean = true,
            )
        )
    }
}
