package com.crowdproj.ad.api.v1.requests

import kotlinx.serialization.KSerializer
import com.crowdproj.ad.api.v1.models.AdSearchRequest
import com.crowdproj.ad.api.v1.models.IRequestAd
import kotlin.reflect.KClass

object SearchRequestStrategy: IRequestStrategy {
    override val discriminator: String = "search"
    override val clazz: KClass<out IRequestAd> = AdSearchRequest::class
    override val serializer: KSerializer<out IRequestAd> = AdSearchRequest.serializer()
    override fun <T : IRequestAd> fillDiscriminator(req: T): T {
        require(req is AdSearchRequest)
        @Suppress("UNCHECKED_CAST")
        return req.copy(requestType = discriminator) as T
    }
}
