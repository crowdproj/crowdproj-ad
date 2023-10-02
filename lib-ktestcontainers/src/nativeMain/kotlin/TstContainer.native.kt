package com.crowdproj.lib.testcontainers

import com.crowdproj.ads.ydb.start_container

actual class TstContainer actual constructor(
    img: String,
    initBlock: TstContainer.() -> Unit
) {
    actual fun exposePorts(vararg ports: Int) {
        start_container()
    }

    actual suspend fun start() {
    }

    actual suspend fun stop() {
    }

    actual suspend fun awaitRunning(logExpr: Regex) {
    }

}
