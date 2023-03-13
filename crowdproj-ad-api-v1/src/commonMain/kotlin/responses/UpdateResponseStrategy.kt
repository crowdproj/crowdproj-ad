package com.crowdproj.ad.api.v1.responses

import kotlinx.serialization.KSerializer
import com.crowdproj.ad.api.v1.models.AdUpdateResponse
import com.crowdproj.ad.api.v1.models.IResponseAd
import kotlin.reflect.KClass

object UpdateResponseStrategy: IResponseStrategy {
    override val discriminator: String = "update"
    override val clazz: KClass<out IResponseAd> = AdUpdateResponse::class
    override val serializer: KSerializer<out IResponseAd> = AdUpdateResponse.serializer()
    override fun <T : IResponseAd> fillDiscriminator(req: T): T {
        require(req is AdUpdateResponse)
        @Suppress("UNCHECKED_CAST")
        return req.copy(responseType = discriminator) as T
    }
}
