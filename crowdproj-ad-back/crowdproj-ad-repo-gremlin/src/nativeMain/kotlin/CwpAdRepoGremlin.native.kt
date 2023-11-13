package com.crowdproj.ad.backend.repository.gremlin

import com.crowdproj.ad.common.models.CwpAd
import com.crowdproj.ad.common.repo.*

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class CwpAdRepoGremlin actual constructor(private val conf: CwpAdRepoGremlinConf) : IAdRepository {
    actual val initializedObjects: List<CwpAd>
        get() = TODO("Not yet implemented ${conf.hosts}")

    actual fun save(ad: CwpAd): CwpAd {
        TODO("Not yet implemented")
    }

    actual override suspend fun createAd(rq: DbAdRequest): DbAdResponse {
        TODO("Not yet implemented")
    }

    actual override suspend fun readAd(rq: DbAdIdRequest): DbAdResponse {
        TODO("Not yet implemented")
    }

    actual override suspend fun updateAd(rq: DbAdRequest): DbAdResponse {
        TODO("Not yet implemented")
    }

    actual override suspend fun deleteAd(rq: DbAdIdRequest): DbAdResponse {
        TODO("Not yet implemented")
    }

    /**
     * Поиск объявлений по фильтру
     * Если в фильтре не установлен какой-либо из параметров - по нему фильтрация не идет
     */
    actual override suspend fun searchAd(rq: DbAdFilterRequest): DbAdsResponse {
        TODO("Not yet implemented")
    }

}
