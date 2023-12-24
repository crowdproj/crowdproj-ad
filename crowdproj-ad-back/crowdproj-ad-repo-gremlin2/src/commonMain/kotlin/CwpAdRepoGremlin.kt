package com.crowdproj.ad.backend.repo.gremlin

import com.crowdproj.ad.common.models.CwpAd
import com.crowdproj.ad.common.repo.*


@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class CwpAdRepoGremlin(conf: CwpAdRepoGremlinConf) : IAdRepository {

    val initializedObjects: List<CwpAd>

    fun save(ad: CwpAd): CwpAd
    override suspend fun createAd(rq: DbAdRequest): DbAdResponse
    override suspend fun readAd(rq: DbAdIdRequest): DbAdResponse
    override suspend fun updateAd(rq: DbAdRequest): DbAdResponse
    override suspend fun deleteAd(rq: DbAdIdRequest): DbAdResponse
    /**
     * Поиск объявлений по фильтру
     * Если в фильтре не установлен какой-либо из параметров - по нему фильтрация не идет
     */
    override suspend fun searchAd(rq: DbAdFilterRequest): DbAdsResponse
}
