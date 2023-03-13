package com.crowdproj.ad.api.v1.responses

import kotlinx.serialization.KSerializer
import com.crowdproj.ad.api.v1.models.AdSearchResponse
import com.crowdproj.ad.api.v1.models.IResponseAd
import kotlin.reflect.KClass

object SearchResponseStrategy: IResponseStrategy {
    override val discriminator: String = "search"
    override val clazz: KClass<out IResponseAd> = AdSearchResponse::class
    override val serializer: KSerializer<out IResponseAd> = AdSearchResponse.serializer()
    override fun <T : IResponseAd> fillDiscriminator(req: T): T {
        require(req is AdSearchResponse)
        @Suppress("UNCHECKED_CAST")
        return req.copy(responseType = discriminator) as T
    }
}
