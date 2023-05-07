package com.crowdproj.ad.repo.tests

import com.crowdproj.ad.common.models.CwpAd
import com.crowdproj.ad.common.models.CwpAdDealSide
import com.crowdproj.ad.common.models.CwpAdUserId
import com.crowdproj.ad.common.repo.DbAdFilterRequest
import com.crowdproj.ad.common.repo.IAdRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.test.Test
import kotlin.test.assertEquals


@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoAdOffersTest {
    abstract val repo: IAdRepository
//
//    protected open val initializedObjects: List<CwpAd> = initObjects
//
//    @Test
//    fun searchOwner() = runRepoTest {
//        val result = repo.searchAd(DbAdFilterRequest(ownerId = searchOwnerId))
//        assertEquals(true, result.isSuccess)
//        val expected = listOf(initializedObjects[1], initializedObjects[3]).sortedBy { it.id.asString() }
//        assertEquals(expected, result.data?.sortedBy { it.id.asString() })
//        assertEquals(emptyList(), result.errors)
//    }
//
//    @Test
//    fun searchDealSide() = runRepoTest {
//        val result = repo.searchAd(DbAdFilterRequest(dealSide = CwpAdDealSide.SUPPLY))
//        assertEquals(true, result.isSuccess)
//        val expected = listOf(initializedObjects[2], initializedObjects[4]).sortedBy { it.id.asString() }
//        assertEquals(expected, result.data?.sortedBy { it.id.asString() })
//        assertEquals(emptyList(), result.errors)
//    }

    companion object: BaseInitAds("search") {

        val searchOwnerId = CwpAdUserId("owner-124")
        override val initObjects: List<CwpAd> = listOf(
            createInitTestModel("ad1"),
            createInitTestModel("ad2", ownerId = searchOwnerId),
            createInitTestModel("ad3", adType = CwpAdDealSide.SUPPLY),
            createInitTestModel("ad4", ownerId = searchOwnerId),
            createInitTestModel("ad5", adType = CwpAdDealSide.SUPPLY),
        )
    }
}
