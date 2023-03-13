package com.crowdproj.ad.api.v1.requests

import kotlinx.serialization.KSerializer
import com.crowdproj.ad.api.v1.models.AdUpdateRequest
import com.crowdproj.ad.api.v1.models.IRequestAd
import kotlin.reflect.KClass

object UpdateRequestStrategy: IRequestStrategy {
    override val discriminator: String = "update"
    override val clazz: KClass<out IRequestAd> = AdUpdateRequest::class
    override val serializer: KSerializer<out IRequestAd> = AdUpdateRequest.serializer()
    override fun <T : IRequestAd> fillDiscriminator(req: T): T {
        require(req is AdUpdateRequest)
        @Suppress("UNCHECKED_CAST")
        return req.copy(requestType = discriminator) as T
    }
}
