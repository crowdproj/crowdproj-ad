package com.crowdproj.ad.repo.tests

import com.crowdproj.ad.common.models.CwpAd
import com.crowdproj.ad.common.models.CwpAdId
import com.crowdproj.ad.common.repo.DbAdIdRequest
import com.crowdproj.ad.common.repo.IAdRepository
import kotlin.test.Test
import kotlin.test.assertEquals

abstract class RepoAdDeleteTest {
    abstract val repo: IAdRepository
    protected open val deleteSucc = initObjects[0]
    protected open val deleteConc = initObjects[1]
    protected open val notFoundId = CwpAdId("ad-repo-delete-notFound")

    @Test
    fun deleteSuccess() = runRepoTest {
        val lockOld = deleteSucc.lock
        val result = repo.deleteAd(DbAdIdRequest(deleteSucc.id, lock = lockOld))

        assertEquals(true, result.isSuccess)
        assertEquals(emptyList(), result.errors)
        assertEquals(lockOld, result.data?.lock)
    }

    @Test
    fun deleteNotFound() = runRepoTest {
        val result = repo.readAd(DbAdIdRequest(notFoundId, lock = lockOld))

        assertEquals(false, result.isSuccess)
        assertEquals(null, result.data)
        val error = result.errors.find { it.code == "not-found" }
        assertEquals("id", error?.field)
    }

    @Test
    fun deleteConcurrency() = runRepoTest {
        val lockOld = deleteSucc.lock
        val result = repo.deleteAd(DbAdIdRequest(deleteConc.id, lock = lockBad))

        assertEquals(false, result.isSuccess)
        val error = result.errors.find { it.code == "concurrency" }
        assertEquals("lock", error?.field)
        assertEquals(lockOld, result.data?.lock)
    }

    companion object : BaseInitAds("delete") {
        override val initObjects: List<CwpAd> = listOf(
            createInitTestModel("delete"),
            createInitTestModel("deleteLock"),
        )
    }
}
