package com.crowdproj.ad.api.v0

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import com.crowdproj.ad.api.v0.models.*

@OptIn(ExperimentalSerializationApi::class)
val apiV2Mapper = Json {
    classDiscriminator = "_"
    encodeDefaults = true
    ignoreUnknownKeys = true

    serializersModule = SerializersModule {
        polymorphicDefaultSerializer(IRequestAd::class) {
            @Suppress("UNCHECKED_CAST")
            when(it) {
                is AdCreateRequest ->  RequestSerializer(AdCreateRequest.serializer()) as SerializationStrategy<IRequestAd>
                is AdReadRequest   ->  RequestSerializer(AdReadRequest  .serializer()) as SerializationStrategy<IRequestAd>
                is AdUpdateRequest ->  RequestSerializer(AdUpdateRequest.serializer()) as SerializationStrategy<IRequestAd>
                is AdDeleteRequest ->  RequestSerializer(AdDeleteRequest.serializer()) as SerializationStrategy<IRequestAd>
                is AdSearchRequest ->  RequestSerializer(AdSearchRequest.serializer()) as SerializationStrategy<IRequestAd>
                is AdOffersRequest ->  RequestSerializer(AdOffersRequest.serializer()) as SerializationStrategy<IRequestAd>
                else -> null
            }
        }
        polymorphicDefaultSerializer(IResponseAd::class) {
            @Suppress("UNCHECKED_CAST")
            when(it) {
                is AdCreateResponse ->  ResponseSerializer(AdCreateResponse.serializer()) as SerializationStrategy<IResponseAd>
                is AdReadResponse   ->  ResponseSerializer(AdReadResponse  .serializer()) as SerializationStrategy<IResponseAd>
                is AdUpdateResponse ->  ResponseSerializer(AdUpdateResponse.serializer()) as SerializationStrategy<IResponseAd>
                is AdDeleteResponse ->  ResponseSerializer(AdDeleteResponse.serializer()) as SerializationStrategy<IResponseAd>
                is AdSearchResponse ->  ResponseSerializer(AdSearchResponse.serializer()) as SerializationStrategy<IResponseAd>
                is AdOffersResponse ->  ResponseSerializer(AdOffersResponse.serializer()) as SerializationStrategy<IResponseAd>
                else -> null
            }
        }

        contextual(AdRequestSerializer)
        contextual(AdResponseSerializer)
    }
}
