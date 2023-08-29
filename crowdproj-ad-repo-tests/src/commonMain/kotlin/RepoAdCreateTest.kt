package com.crowdproj.ad.repo.tests

import com.crowdproj.ad.common.models.*
import com.crowdproj.ad.common.repo.DbAdRequest
import com.crowdproj.ad.common.repo.IAdRepository
import com.crowdproj.ad.repo.tests.BaseInitAds
import com.crowdproj.ad.repo.tests.runRepoTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals


@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoAdCreateTest {
    abstract val repo: IAdRepository

    protected open val lockNew: CwpAdLock = CwpAdLock("20000000-0000-0000-0000-000000000002")

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
        assertEquals(lockNew, result.data?.lock)
    }

    companion object : BaseInitAds("create") {
        override val initObjects: List<CwpAd> = emptyList()
    }
}
