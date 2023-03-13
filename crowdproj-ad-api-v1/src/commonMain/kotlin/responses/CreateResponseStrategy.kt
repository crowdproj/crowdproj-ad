package com.crowdproj.ad.api.v1.responses

import kotlinx.serialization.KSerializer
import com.crowdproj.ad.api.v1.models.AdCreateResponse
import com.crowdproj.ad.api.v1.models.IResponseAd
import kotlin.reflect.KClass

object CreateResponseStrategy: IResponseStrategy {
    override val discriminator: String = "create"
    override val clazz: KClass<out IResponseAd> = AdCreateResponse::class
    override val serializer: KSerializer<out IResponseAd> = AdCreateResponse.serializer()
    override fun <T : IResponseAd> fillDiscriminator(req: T): T {
        require(req is AdCreateResponse)
        @Suppress("UNCHECKED_CAST")
        return req.copy(responseType = discriminator) as T
    }
}
