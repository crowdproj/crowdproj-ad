package com.crowdproj.ad.api.v0.requests

import kotlinx.serialization.KSerializer
import com.crowdproj.ad.api.v0.models.AdReadRequest
import com.crowdproj.ad.api.v0.models.IRequestAd
import kotlin.reflect.KClass

object ReadRequestStrategy: IRequestStrategy {
    override val discriminator: String = "read"
    override val clazz: KClass<out IRequestAd> = AdReadRequest::class
    override val serializer: KSerializer<out IRequestAd> = AdReadRequest.serializer()
    override fun <T : IRequestAd> fillDiscriminator(req: T): T {
        require(req is AdReadRequest)
        @Suppress("UNCHECKED_CAST")
        return req.copy(requestType = discriminator) as T
    }
}
