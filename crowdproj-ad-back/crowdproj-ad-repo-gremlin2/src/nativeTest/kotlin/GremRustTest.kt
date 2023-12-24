package com.crowdproj.ad.backend.repo.gremlin

import com.crowdproj.ad.common.models.*
import kotlin.test.Test

class GremRustTest {

    @Test
    fun create() {
        println("KT START")
        val conf = CwpAdRepoGremlinConf(
            hosts = "localhost",
            pass = "root_root",
        )
        val repo = CwpAdRepoGremlin(conf)
        val ad = CwpAd(
            lock = CwpAdLock("xx"),
            title = "Test Title",
            description = "Test Description",
            ownerId = CwpAdUserId("user123"),
            adType = CwpAdDealSide.SUPPLY,
            visibility = CwpAdVisibility.VISIBLE_PUBLIC,
            productId = CwpAdProductId("567"),
        )
        repo.save(ad)
        repo.close()
    }
}
