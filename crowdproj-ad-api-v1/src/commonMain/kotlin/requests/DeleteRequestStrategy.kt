package com.crowdproj.ad.api.v0.requests

import kotlinx.serialization.KSerializer
import com.crowdproj.ad.api.v0.models.AdDeleteRequest
import com.crowdproj.ad.api.v0.models.IRequestAd
import kotlin.reflect.KClass

object DeleteRequestStrategy: IRequestStrategy {
    override val discriminator: String = "delete"
    override val clazz: KClass<out IRequestAd> = AdDeleteRequest::class
    override val serializer: KSerializer<out IRequestAd> = AdDeleteRequest.serializer()
    override fun <T : IRequestAd> fillDiscriminator(req: T): T {
        require(req is AdDeleteRequest)
        @Suppress("UNCHECKED_CAST")
        return req.copy(requestType = discriminator) as T
    }
}
