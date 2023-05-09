package ru.otus.otuskotlin.marketplace.backend.repository.inmemory

import com.crowdproj.ad.common.models.CwpAdDealSide
import com.crowdproj.ad.common.repo.*
import com.crowdproj.ad.stubs.CwpAdStub

class CwpAdRepoStub() : IAdRepository {
    override suspend fun createAd(rq: DbAdRequest): DbAdResponse {
        return DbAdResponse(
            data = CwpAdStub.prepareResult {  },
            isSuccess = true,
        )
    }

    override suspend fun readAd(rq: DbAdIdRequest): DbAdResponse {
        return DbAdResponse(
            data = CwpAdStub.prepareResult {  },
            isSuccess = true,
        )
    }

    override suspend fun updateAd(rq: DbAdRequest): DbAdResponse {
        return DbAdResponse(
            data = CwpAdStub.prepareResult {  },
            isSuccess = true,
        )
    }

    override suspend fun deleteAd(rq: DbAdIdRequest): DbAdResponse {
        return DbAdResponse(
            data = CwpAdStub.prepareResult {  },
            isSuccess = true,
        )
    }

    override suspend fun searchAd(rq: DbAdFilterRequest): DbAdsResponse {
        return DbAdsResponse(
            data = CwpAdStub.prepareSearchList(filter = "", CwpAdDealSide.DEMAND),
            isSuccess = true,
        )
    }
}
