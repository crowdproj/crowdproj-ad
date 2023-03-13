package com.crowdproj.ad.api.v1.requests

import kotlinx.serialization.KSerializer
import com.crowdproj.ad.api.v1.models.AdCreateRequest
import com.crowdproj.ad.api.v1.models.IRequestAd
import kotlin.reflect.KClass

object CreateRequestStrategy: IRequestStrategy {
    override val discriminator: String = "create"
    override val clazz: KClass<out IRequestAd> = AdCreateRequest::class
    override val serializer: KSerializer<out IRequestAd> = AdCreateRequest.serializer()
    override fun <T : IRequestAd> fillDiscriminator(req: T): T {
        require(req is AdCreateRequest)
        @Suppress("UNCHECKED_CAST")
        return req.copy(requestType = discriminator) as T
    }
}
