package com.crowdproj.lib.testcontainers

actual class TstContainer actual constructor(
    img: String,
    initBlock: TstContainer.() -> Unit
) {
    actual fun exposePorts(vararg ports: Int) {
    }

    actual suspend fun start() {
    }

    actual suspend fun stop() {
    }

    actual suspend fun awaitRunning(logExpr: Regex) {
    }

}
