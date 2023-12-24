package com.crowdproj.ad.backend.repo.gremlin

import com.crowdproj.ad.common.models.CwpAd
import com.crowdproj.ad.common.models.CwpAdId
import com.crowdproj.ad.repo.tests.RepoAdReadTest
import kotlin.random.Random
import kotlin.random.nextUInt

class AdRepoGremlinReadTest : RepoAdReadTest() {
    override val repo: CwpAdRepoGremlin by lazy {
        CwpAdRepoGremlin(
            CwpAdRepoGremlinConf(
                hosts = ArcadeDbContainer.container.host,
                port = ArcadeDbContainer.container.getMappedPort(2480),
                user = ArcadeDbContainer.username,
                pass = ArcadeDbContainer.password,
                enableSsl = false,
                database = "read_${Random.Default.nextUInt(1_000_000u)}",
                initObjects = initObjects,
                mustClean = true,
            )
        )
    }
    override val readSucc: CwpAd by lazy { repo.initializedObjects[0] }
    override val notFoundId: CwpAdId = CwpAdId("#1:98")
}
