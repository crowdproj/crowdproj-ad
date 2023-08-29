package com.crowdproj.ad.repo.ydb

import com.benasher44.uuid.uuid4
import com.crowdproj.ad.common.models.CwpAd
import org.testcontainers.containers.GenericContainer
import java.time.Duration

class YdbContainer : GenericContainer<YdbContainer>("cr.yandex/yc/yandex-docker-local-ydb:latest")

object YdbTestCompanion {
    private const val DB_PATH = "/local"
    private val container by lazy {
        YdbContainer().apply {

            withStartupTimeout(Duration.ofSeconds(300L))
            start()
        }
    }

    private val host: String by lazy { container.host }
    private val port: Int by lazy { container.getMappedPort(2136) }
    private val path: String = DB_PATH

    fun repoUnderTestContainer(
        initObjects: Collection<CwpAd> = emptyList(),
        randomUuid: () -> String = { uuid4().toString() },
    ): CwpAdRepoYdb {
        return CwpAdRepoYdb(
//            YdbProperties(url, USER, PASS, SCHEMA, dropDatabase = true),
            initObjects = initObjects,
            randomUuid = randomUuid
        )
    }
}
