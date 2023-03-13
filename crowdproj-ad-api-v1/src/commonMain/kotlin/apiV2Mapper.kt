package com.crowdproj.ad.api.v1

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import com.crowdproj.ad.api.v1.models.*
import com.crowdproj.ad.api.v1.requests.IRequestStrategy
import com.crowdproj.ad.api.v1.responses.IResponseStrategy

@OptIn(ExperimentalSerializationApi::class)
val apiV2Mapper = Json {
    classDiscriminator = "_"
    encodeDefaults = true
    ignoreUnknownKeys = true

    serializersModule = SerializersModule {
        polymorphicDefaultSerializer(IRequestAd::class) {
            val strategy = IRequestStrategy.membersByClazz[it::class] ?: return@polymorphicDefaultSerializer null
            @Suppress("UNCHECKED_CAST")
            RequestSerializer(strategy.serializer) as SerializationStrategy<IRequestAd>
        }

        polymorphicDefaultSerializer(IResponseAd::class) {
            val strategy = IResponseStrategy.membersByClazz[it::class] ?: return@polymorphicDefaultSerializer null
            @Suppress("UNCHECKED_CAST")
            ResponseSerializer(strategy.serializer) as SerializationStrategy<IResponseAd>
//            @Suppress("UNCHECKED_CAST")
//            when(it) {
//                is AdCreateResponse ->  ResponseSerializer(AdCreateResponse.serializer()) as SerializationStrategy<IResponseAd>
//                is AdReadResponse   ->  ResponseSerializer(AdReadResponse  .serializer()) as SerializationStrategy<IResponseAd>
//                is AdUpdateResponse ->  ResponseSerializer(AdUpdateResponse.serializer()) as SerializationStrategy<IResponseAd>
//                is AdDeleteResponse ->  ResponseSerializer(AdDeleteResponse.serializer()) as SerializationStrategy<IResponseAd>
//                is AdSearchResponse ->  ResponseSerializer(AdSearchResponse.serializer()) as SerializationStrategy<IResponseAd>
//                is AdOffersResponse ->  ResponseSerializer(AdOffersResponse.serializer()) as SerializationStrategy<IResponseAd>
//                else -> null
//            }
        }

        contextual(AdRequestSerializer)
        contextual(AdResponseSerializer)
    }
}
