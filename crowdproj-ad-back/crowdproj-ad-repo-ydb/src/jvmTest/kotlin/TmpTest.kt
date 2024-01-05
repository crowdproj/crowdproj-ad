package com.crowdproj.ad.repo.ydb

import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.test.runTest
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutionException
import java.util.concurrent.Executors
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.test.Test

class TmpTest {
    @Test
    fun x() = runTest {
        val ex = Executors.newSingleThreadExecutor()
        val x = CompletableFuture<String>()
            .completeAsync {
                Thread.sleep(5000)
                "done"
            }
        val cor = x.myAwait()
        println("DONE: $cor")
    }

    suspend fun <T> CompletableFuture<T>.myAwait(): T {
        println("myAwait")
        if (this.isDone) {
            try {
                @Suppress("UNCHECKED_CAST", "BlockingMethodInNonBlockingContext")
                return this.get() as T
            } catch (e: ExecutionException) {
                throw e.cause ?: e // unwrap original cause from ExecutionException
            }
        }
        // slow path -- suspend
        return suspendCancellableCoroutine { cont: CancellableContinuation<T> ->
            handle { t, throwable ->
                if (throwable == null) {
                    cont.resume(t)
                } else {
                    cont.resumeWithException(throwable)
                }
            }
            cont.invokeOnCancellation {
                this.cancel(false)
            }
        }
    }
}
