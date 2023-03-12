package com.crowdproj.ad.api.v0.requests

import com.crowdproj.ad.api.v0.models.IRequestAd
import com.crowdproj.ad.api.v0.IApiStrategy


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
