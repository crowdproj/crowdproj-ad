package com.crowdproj.lib.testcontainers

import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.time.Duration.Companion.minutes

class ContainersTest {
    @Test
    fun test() = runTest(timeout = 10.minutes) {
        val cnt = TstContainer("cr.yandex/yc/yandex-docker-local-ydb:latest") {
            exposePorts(2135, 8765, 2136)
        }
        cnt.start()
        cnt.awaitRunning(Regex("sdf"))
        cnt.stop()
    }
}
