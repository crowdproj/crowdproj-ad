package com.crowdproj.ad.api.v0.responses

import com.crowdproj.ad.api.v0.models.IResponseAd
import com.crowdproj.ad.api.v0.IApiStrategy

sealed interface IResponseStrategy: IApiStrategy<IResponseAd> {
    companion object {
        val members = listOf(
            CreateResponseStrategy,
            ReadResponseStrategy,
            UpdateResponseStrategy,
            DeleteResponseStrategy,
            SearchResponseStrategy,
            OffersResponseStrategy,
        )
        val membersByDiscriminator = members.associateBy { it.discriminator }
        val membersByClazz = members.associateBy { it.clazz }
    }
}
