package com.crowdproj.ad.common.repo

interface IAdRepository {
    suspend fun createAd(rq: DbAdRequest): DbAdResponse
    suspend fun readAd(rq: DbAdIdRequest): DbAdResponse
    suspend fun updateAd(rq: DbAdRequest): DbAdResponse
    suspend fun deleteAd(rq: DbAdIdRequest): DbAdResponse
    suspend fun searchAd(rq: DbAdFilterRequest): DbAdsResponse
    companion object {
        val NONE = object : IAdRepository {
            inner class NoneRepository(): RuntimeException("Repository is not set")
            suspend fun forbidden(): Nothing = throw NoneRepository()
            override suspend fun createAd(rq: DbAdRequest): DbAdResponse {
                forbidden()
            }

            override suspend fun readAd(rq: DbAdIdRequest): DbAdResponse {
                forbidden()
            }

            override suspend fun updateAd(rq: DbAdRequest): DbAdResponse {
                forbidden()
            }

            override suspend fun deleteAd(rq: DbAdIdRequest): DbAdResponse {
                forbidden()
            }

            override suspend fun searchAd(rq: DbAdFilterRequest): DbAdsResponse {
                forbidden()
            }
        }
    }
}
