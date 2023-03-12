package com.crowdproj.ad.api.v0.responses

import kotlinx.serialization.KSerializer
import com.crowdproj.ad.api.v0.models.AdOffersResponse
import com.crowdproj.ad.api.v0.models.IResponseAd
import kotlin.reflect.KClass

object OffersResponseStrategy: IResponseStrategy {
    override val discriminator: String = "offers"
    override val clazz: KClass<out IResponseAd> = AdOffersResponse::class
    override val serializer: KSerializer<out IResponseAd> = AdOffersResponse.serializer()
    override fun <T : IResponseAd> fillDiscriminator(req: T): T {
        require(req is AdOffersResponse)
        @Suppress("UNCHECKED_CAST")
        return req.copy(responseType = discriminator) as T
    }
}
