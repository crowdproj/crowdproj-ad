package com.crowdproj.ad.api.v1.requests

import com.crowdproj.ad.api.v1.models.IRequestAd
import com.crowdproj.ad.api.v1.IApiStrategy


sealed interface IRequestStrategy: IApiStrategy<IRequestAd> {
    companion object {
        private val members: List<IRequestStrategy> = listOf(
            CreateRequestStrategy,
            ReadRequestStrategy,
            UpdateRequestStrategy,
            DeleteRequestStrategy,
            SearchRequestStrategy,
            OffersRequestStrategy,
        )
        val membersByDiscriminator = members.associateBy { it.discriminator }
        val membersByClazz = members.associateBy { it.clazz }
    }
}
