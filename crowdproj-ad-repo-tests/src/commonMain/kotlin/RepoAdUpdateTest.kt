package com.crowdproj.ad.repo.tests

import com.crowdproj.ad.common.models.*
import com.crowdproj.ad.common.repo.DbAdRequest
import com.crowdproj.ad.common.repo.IAdRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.test.Test
import kotlin.test.assertEquals


@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoAdUpdateTest {
    abstract val repo: IAdRepository
    protected open val updateSucc = initObjects[0]
    private val updateIdNotFound = CwpAdId("ad-repo-update-not-found")

    private val reqUpdateSucc by lazy {
        CwpAd(
            id = updateSucc.id,
            title = "update object",
            description = "update object description",
            ownerId = CwpAdUserId("owner-123"),
            visibility = CwpAdVisibility.VISIBLE_TO_GROUP,
            adType = CwpAdDealSide.SUPPLY,
        )
    }
    private val reqUpdateNotFound = CwpAd(
        id = updateIdNotFound,
        title = "update object not found",
        description = "update object not found description",
        ownerId = CwpAdUserId("owner-123"),
        visibility = CwpAdVisibility.VISIBLE_TO_GROUP,
        adType = CwpAdDealSide.SUPPLY,
    )

    @Test
    fun updateSuccess() = runRepoTest {
        val result = repo.updateAd(DbAdRequest(reqUpdateSucc))
        assertEquals(true, result.isSuccess)
        assertEquals(reqUpdateSucc.id, result.data?.id)
        assertEquals(reqUpdateSucc.title, result.data?.title)
        assertEquals(reqUpdateSucc.description, result.data?.description)
        assertEquals(reqUpdateSucc.adType, result.data?.adType)
        assertEquals(emptyList(), result.errors)
    }

    @Test
    fun updateNotFound() = runRepoTest {
        val result = repo.updateAd(DbAdRequest(reqUpdateNotFound))
        assertEquals(false, result.isSuccess)
        assertEquals(null, result.data)
        val error = result.errors.find { it.code == "not-found" }
        assertEquals("id", error?.field)
    }

    companion object : BaseInitAds("update") {
        override val initObjects: List<CwpAd> = listOf(
            createInitTestModel("update"),
            createInitTestModel("updateConc"),
        )
    }
}
