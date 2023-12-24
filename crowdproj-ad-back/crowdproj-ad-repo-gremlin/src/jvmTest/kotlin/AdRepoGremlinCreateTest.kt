package com.crowdproj.ad.backend.repo.gremlin

import com.crowdproj.ad.common.repo.IAdRepository
import com.crowdproj.ad.repo.tests.RepoAdCreateTest
import com.crowdproj.ad.repo.tests.RepoAdSearchTest
import kotlin.random.Random
import kotlin.random.nextUInt

class AdRepoGremlinCreateTest : RepoAdCreateTest() {
    override val repo: IAdRepository by lazy {
        CwpAdRepoGremlin(
            CwpAdRepoGremlinConf(
                hosts = ArcadeDbContainer.container.host,
                port = ArcadeDbContainer.container.getMappedPort(2480),
                enableSsl = false,
                user = ArcadeDbContainer.username,
                pass = ArcadeDbContainer.password,
                database = "create_${Random.Default.nextUInt(1_000_000u)}",
                initObjects = RepoAdSearchTest.initObjects,
                randomUuid = { lockNew.asString() },
                mustClean = true,
            )
        )
    }
}
