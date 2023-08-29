package com.crowdproj.ad.repo.tests

import com.crowdproj.ad.common.models.*
import com.crowdproj.ad.common.repo.DbAdRequest
import com.crowdproj.ad.common.repo.IAdRepository
import com.crowdproj.ad.repo.tests.BaseInitAds
import com.crowdproj.ad.repo.tests.runRepoTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.test.Test
import kotlin.test.assertEquals


@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoAdUpdateTest {
    abstract val repo: IAdRepository
    protected open val updateSucc = initObjects[0]
    protected open val updateConc = initObjects[1]
    protected val updateIdNotFound = CwpAdId("ad-repo-update-not-found")
    protected val lockBad = CwpAdLock("20000000-0000-0000-0000-000000000009")
    protected val lockNew = CwpAdLock("20000000-0000-0000-0000-000000000002")

    private val reqUpdateSucc by lazy {
        CwpAd(
            id = updateSucc.id,
            title = "update object",
            description = "update object description",
            ownerId = CwpAdUserId("owner-123"),
            visibility = CwpAdVisibility.VISIBLE_TO_GROUP,
            adType = CwpAdDealSide.SUPPLY,
            lock = initObjects.first().lock,
        )
    }
    private val reqUpdateNotFound = CwpAd(
        id = updateIdNotFound,
        title = "update object not found",
        description = "update object not found description",
        ownerId = CwpAdUserId("owner-123"),
        visibility = CwpAdVisibility.VISIBLE_TO_GROUP,
        adType = CwpAdDealSide.SUPPLY,
        lock = initObjects.first().lock,
    )
    private val reqUpdateConc by lazy {
        CwpAd(
            id = updateConc.id,
            title = "update object not found",
            description = "update object not found description",
            ownerId = CwpAdUserId("owner-123"),
            visibility = CwpAdVisibility.VISIBLE_TO_GROUP,
            adType = CwpAdDealSide.SUPPLY,
            lock = lockBad,
        )
    }

    @Test
    fun updateSuccess() = runRepoTest {
        val result = repo.updateAd(DbAdRequest(reqUpdateSucc))
        assertEquals(true, result.isSuccess)
        assertEquals(reqUpdateSucc.id, result.data?.id)
        assertEquals(reqUpdateSucc.title, result.data?.title)
        assertEquals(reqUpdateSucc.description, result.data?.description)
        assertEquals(reqUpdateSucc.adType, result.data?.adType)
        assertEquals(emptyList(), result.errors)
        assertEquals(lockNew, result.data?.lock)
    }

    @Test
    fun updateNotFound() = runRepoTest {
        val result = repo.updateAd(DbAdRequest(reqUpdateNotFound))
        assertEquals(false, result.isSuccess)
        assertEquals(null, result.data)
        val error = result.errors.find { it.code == "not-found" }
        assertEquals("id", error?.field)
    }

    @Test
    fun updateConcurrencyError() = runRepoTest {
        val result = repo.updateAd(DbAdRequest(reqUpdateConc))
        assertEquals(false, result.isSuccess)
        val error = result.errors.find { it.code == "concurrency" }
        assertEquals("lock", error?.field)
        assertEquals(updateConc, result.data)
    }

    companion object : BaseInitAds("update") {
        override val initObjects: List<CwpAd> = listOf(
            createInitTestModel("update"),
            createInitTestModel("updateConc"),
        )
    }
}
