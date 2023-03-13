package com.crowdproj.ad.api.v1

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import com.crowdproj.ad.api.v1.models.IResponseAd
import com.crowdproj.ad.api.v1.responses.IResponseStrategy


val AdResponseSerializer = ResponseSerializer(AdResponseSerializerBase)

private object AdResponseSerializerBase : JsonContentPolymorphicSerializer<IResponseAd>(IResponseAd::class) {
    private const val discriminator = "requestType"

    override fun selectDeserializer(element: JsonElement): KSerializer<out IResponseAd> {

        val discriminatorValue = element.jsonObject[discriminator]?.jsonPrimitive?.content
        return IResponseStrategy.membersByDiscriminator[discriminatorValue]?.serializer
            ?: throw SerializationException(
                "Unknown value '${discriminatorValue}' in discriminator '$discriminator' " +
                        "property of ${IResponseAd::class} implementation"
            )
    }
}

class ResponseSerializer<T : IResponseAd>(private val serializer: KSerializer<T>) : KSerializer<T> by serializer {
    override fun serialize(encoder: Encoder, value: T) =
        IResponseStrategy
            .membersByClazz[value::class]
            ?.fillDiscriminator(value)
            ?.let { serializer.serialize(encoder, it) }
            ?: throw SerializationException(
                "Unknown class to serialize as IResponseAd instance in ResponseSerializer"
            )
}
