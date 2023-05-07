package com.crowdproj.ad.repo.tests

import com.crowdproj.ad.common.models.*
import com.crowdproj.ad.common.repo.DbAdRequest
import com.crowdproj.ad.common.repo.IAdRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals


@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoAdCreateTest {
    abstract val repo: IAdRepository

    private val createObj = CwpAd(
        title = "create object",
        description = "create object description",
        ownerId = CwpAdUserId("owner-123"),
        visibility = CwpAdVisibility.VISIBLE_TO_GROUP,
        adType = CwpAdDealSide.SUPPLY,
    )

    @Test
    fun createSuccess() = runRepoTest {
        val result = repo.createAd(DbAdRequest(createObj))
        val expected = createObj.copy(id = result.data?.id ?: CwpAdId.NONE)
        assertEquals(true, result.isSuccess)
        assertEquals(expected.title, result.data?.title)
        assertEquals(expected.description, result.data?.description)
        assertEquals(expected.adType, result.data?.adType)
        assertNotEquals(CwpAdId.NONE, result.data?.id)
        assertEquals(emptyList(), result.errors)
    }

    companion object : BaseInitAds("create") {
        override val initObjects: List<CwpAd> = emptyList()
    }
}
