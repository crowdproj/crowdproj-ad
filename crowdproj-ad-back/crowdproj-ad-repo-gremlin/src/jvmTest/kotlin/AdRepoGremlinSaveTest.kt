package com.crowdproj.ad.backend.repo.gremlin

import com.crowdproj.ad.common.models.*
import kotlin.random.Random
import kotlin.random.nextUInt
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class AdRepoGremlinSaveTest() {
    private val testElement = CwpAd(
        title = "title 1",
        description = "descr 1",
        adType = CwpAdDealSide.DEMAND,
        visibility = CwpAdVisibility.VISIBLE_PUBLIC,
        ownerId = CwpAdUserId("2342423523"),
        productId = CwpAdProductId("234234324"),
    )
    val repo: CwpAdRepoGremlin by lazy {
        CwpAdRepoGremlin(
            CwpAdRepoGremlinConf(
                hosts = ArcadeDbContainer.container.host,
                port = ArcadeDbContainer.container.getMappedPort(2480),
                enableSsl = false,
                user = ArcadeDbContainer.username,
                pass = ArcadeDbContainer.password,
                database = "init_${Random.Default.nextUInt(1_000_000u)}",
                initObjects = listOf(testElement),
            )
        )
    }

    @Test
    fun x() {
        val actual = repo.initializedObjects.first()
        assertEquals(testElement, actual.copy(id = CwpAdId.NONE))
        assertNotEquals(CwpAdId.NONE, actual.id)
    }
}
