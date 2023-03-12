package com.crowdproj.ad.api.v0.requests

import kotlinx.serialization.KSerializer
import com.crowdproj.ad.api.v0.models.AdOffersRequest
import com.crowdproj.ad.api.v0.models.IRequestAd
import kotlin.reflect.KClass

object OffersRequestStrategy: IRequestStrategy {
    override val discriminator: String = "offers"
    override val clazz: KClass<out IRequestAd> = AdOffersRequest::class
    override val serializer: KSerializer<out IRequestAd> = AdOffersRequest.serializer()
    override fun <T : IRequestAd> fillDiscriminator(req: T): T {
        require(req is AdOffersRequest)
        @Suppress("UNCHECKED_CAST")
        return req.copy(requestType = discriminator) as T
    }
}
