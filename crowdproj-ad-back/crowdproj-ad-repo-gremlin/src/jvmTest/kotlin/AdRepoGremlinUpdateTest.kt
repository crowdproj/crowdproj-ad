package com.crowdproj.ad.backend.repo.gremlin

import com.crowdproj.ad.common.models.CwpAd
import com.crowdproj.ad.common.models.CwpAdId
import com.crowdproj.ad.repo.tests.RepoAdUpdateTest
import kotlin.random.Random
import kotlin.random.nextUInt

class AdRepoGremlinUpdateTest : RepoAdUpdateTest() {
    override val repo: CwpAdRepoGremlin by lazy {
        CwpAdRepoGremlin(
            CwpAdRepoGremlinConf(
                hosts = ArcadeDbContainer.container.host,
                port = ArcadeDbContainer.container.getMappedPort(2480),
                enableSsl = false,
                user = ArcadeDbContainer.username,
                pass = ArcadeDbContainer.password,
                database = "update_${Random.Default.nextUInt(1_000_000u)}",
                initObjects = initObjects,
                randomUuid = { lockNew.asString() },
                mustClean = true,
            )
        )
    }
    override val updateSucc: CwpAd by lazy { repo.initializedObjects[0] }
    override val updateConc: CwpAd by lazy { repo.initializedObjects[1] }
    override val updateIdNotFound: CwpAdId = CwpAdId("#100:0")
}
