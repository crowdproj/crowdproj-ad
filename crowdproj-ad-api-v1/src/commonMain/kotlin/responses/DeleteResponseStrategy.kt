package com.crowdproj.ad.api.v0.responses

import kotlinx.serialization.KSerializer
import com.crowdproj.ad.api.v0.models.AdDeleteResponse
import com.crowdproj.ad.api.v0.models.IResponseAd
import kotlin.reflect.KClass

object DeleteResponseStrategy: IResponseStrategy {
    override val discriminator: String = "delete"
    override val clazz: KClass<out IResponseAd> = AdDeleteResponse::class
    override val serializer: KSerializer<out IResponseAd> = AdDeleteResponse.serializer()
    override fun <T : IResponseAd> fillDiscriminator(req: T): T {
        require(req is AdDeleteResponse)
        @Suppress("UNCHECKED_CAST")
        return req.copy(responseType = discriminator) as T
    }
}
