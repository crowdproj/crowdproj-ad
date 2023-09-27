package com.crowdproj.lib.testcontainers

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.wait.strategy.LogMessageWaitStrategy
import org.testcontainers.utility.DockerImageName

actual class TstContainer actual constructor(
    img: String,
    initBlock: TstContainer.() -> Unit
) {
    private val tc = GenericContainer(DockerImageName.parse(img))
    actual fun exposePorts(vararg ports: Int) {
        tc.addExposedPorts(*ports)
    }

    actual suspend fun start() {
        withContext(Dispatchers.IO) { tc.start() }
    }

    actual suspend fun stop() {
        withContext(Dispatchers.IO) { tc.stop() }
    }

    actual suspend fun awaitRunning(logExpr: Regex) {
        val ws = LogMessageWaitStrategy().apply {
            withRegEx(logExpr.toString())
            withTimes(1)
        }
        withContext(Dispatchers.IO) { tc.waitingFor(ws); }
    }

}
