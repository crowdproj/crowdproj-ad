package com.crowdproj.ad.repo.ydb

import com.benasher44.uuid.uuid4
import com.crowdproj.ad.common.models.*
import com.crowdproj.ad.common.repo.*
import com.crowdproj.ad.repo.ydb.model.AdEntity

class CwpAdRepoYdb(
    initObjects: Collection<CwpAd> = emptyList(),
    val randomUuid: () -> String = { uuid4().toString() },
) : IAdRepository {

//    private val cache = Cache.Builder<String, AdEntity>()
//        .expireAfterWrite(ttl)
//        .build()

    init {
        initObjects.forEach {
            save(it)
        }
    }

    private fun save(ad: CwpAd) {
        val entity = AdEntity(ad)
        if (entity.id == null) {
            return
        }
//        cache.put(entity.id, entity)
    }

    override suspend fun createAd(rq: DbAdRequest): DbAdResponse {
        val key = randomUuid()
        val ad = rq.ad.copy(id = CwpAdId(key))
        val entity = AdEntity(ad)
//        cache.put(key, entity)
        return DbAdResponse(
            data = ad,
            isSuccess = true,
        )
    }

    override suspend fun readAd(rq: DbAdIdRequest): DbAdResponse {
        val key = rq.id.takeIf { it != CwpAdId.NONE }?.asString() ?: return resultErrorEmptyId
//        return cache.get(key)
//            ?.let {
//                DbAdResponse(
//                    data = it.toInternal(),
//                    isSuccess = true,
//                )
//            } ?: resultErrorNotFound
        TODO()
    }

    override suspend fun updateAd(rq: DbAdRequest): DbAdResponse {
        val key = rq.ad.id.takeIf { it != CwpAdId.NONE }?.asString() ?: return resultErrorEmptyId
        val newAd = rq.ad.copy()
        val entity = AdEntity(newAd)
//        return when (cache.get(key)) {
//            null -> resultErrorNotFound
//            else -> {
//                cache.put(key, entity)
//                DbAdResponse(
//                    data = newAd,
//                    isSuccess = true,
//                )
//            }
//        }
        TODO()
    }

    override suspend fun deleteAd(rq: DbAdIdRequest): DbAdResponse {
        val key = rq.id.takeIf { it != CwpAdId.NONE }?.asString() ?: return resultErrorEmptyId
//        return when (val oldAd = cache.get(key)) {
//            null -> resultErrorNotFound
//            else -> {
//                cache.invalidate(key)
//                DbAdResponse(
//                    data = oldAd.toInternal(),
//                    isSuccess = true,
//                )
//            }
//        }
        TODO()
    }

    override suspend fun searchAd(rq: DbAdFilterRequest): DbAdsResponse {
//        val result = cache.asMap().asSequence()
//            .filter { entry ->
//                rq.ownerId.takeIf { it != CwpAdUserId.NONE }?.let {
//                    it.asString() == entry.value.ownerId
//                } ?: true
//            }
//            .filter { entry ->
//                rq.dealSide.takeIf { it != CwpAdDealSide.NONE }?.let {
//                    it.name == entry.value.adType
//                } ?: true
//            }
//            .filter { entry ->
//                rq.titleFilter.takeIf { it.isNotBlank() }?.let {
//                    entry.value.title?.contains(it) ?: false
//                } ?: true
//            }
//            .map { it.value.toInternal() }
//            .toList()
//        return DbAdsResponse(
//            data = result,
//            isSuccess = true
//        )
        TODO()
    }

    companion object {
        val resultErrorEmptyId = DbAdResponse(
            data = null,
            isSuccess = false,
            errors = listOf(
                CwpAdError(
                    code = "id-empty",
                    group = "validation",
                    field = "id",
                    message = "Id must not be null or blank"
                )
            )
        )
        val resultErrorNotFound = DbAdResponse(
            isSuccess = false,
            data = null,
            errors = listOf(
                CwpAdError(
                    code = "not-found",
                    field = "id",
                    message = "Not Found"
                )
            )
        )
    }
}
