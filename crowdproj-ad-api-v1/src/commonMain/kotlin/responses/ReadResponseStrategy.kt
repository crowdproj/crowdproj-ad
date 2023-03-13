package com.crowdproj.ad.api.v1.responses

import kotlinx.serialization.KSerializer
import com.crowdproj.ad.api.v1.models.AdReadResponse
import com.crowdproj.ad.api.v1.models.IResponseAd
import kotlin.reflect.KClass

object ReadResponseStrategy: IResponseStrategy {
    override val discriminator: String = "read"
    override val clazz: KClass<out IResponseAd> = AdReadResponse::class
    override val serializer: KSerializer<out IResponseAd> = AdReadResponse.serializer()
    override fun <T : IResponseAd> fillDiscriminator(req: T): T {
        require(req is AdReadResponse)
        @Suppress("UNCHECKED_CAST")
        return req.copy(responseType = discriminator) as T
    }
}
