package com.crowdproj.lib.testcontainers

expect class TstContainer(img: String, initBlock: TstContainer.() -> Unit) {

    fun exposePorts(vararg ports: Int)
    suspend fun start()
    suspend fun stop()
    suspend fun awaitRunning(logExpr: Regex)
}
