package com.crowdproj.ad.api.v1

import com.crowdproj.ad.api.v1.models.IRequestAd
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import com.crowdproj.ad.api.v1.requests.IRequestStrategy


val AdRequestSerializer = RequestSerializer(AdRequestSerializerBase)

private object AdRequestSerializerBase : JsonContentPolymorphicSerializer<IRequestAd>(IRequestAd::class) {
    private const val discriminator = "requestType"

    override fun selectDeserializer(element: JsonElement): KSerializer<out IRequestAd> {

        val discriminatorValue = element.jsonObject[discriminator]?.jsonPrimitive?.content
        return IRequestStrategy.membersByDiscriminator[discriminatorValue]?.serializer
            ?: throw SerializationException(
                "Unknown value '${discriminatorValue}' in discriminator '$discriminator' " +
                        "property of ${IRequestAd::class} implementation"
            )
    }
}

class RequestSerializer<T : IRequestAd>(private val serializer: KSerializer<T>) : KSerializer<T> by serializer {
    override fun serialize(encoder: Encoder, value: T) =
        IRequestStrategy
            .membersByClazz[value::class]
            ?.fillDiscriminator(value)
            ?.let { serializer.serialize(encoder, it) }
            ?: throw SerializationException(
                "Unknown class to serialize as IRequestAd instance in RequestSerializer"
            )
}
