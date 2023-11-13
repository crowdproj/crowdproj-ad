package com.crowdproj.ad.backend.repository.gremlin.mappers

import com.crowdproj.ad.backend.repository.gremlin.CwpAdGremlinConst.FIELD_AD_TYPE
import com.crowdproj.ad.backend.repository.gremlin.CwpAdGremlinConst.FIELD_DESCRIPTION
import com.crowdproj.ad.backend.repository.gremlin.CwpAdGremlinConst.FIELD_ID
import com.crowdproj.ad.backend.repository.gremlin.CwpAdGremlinConst.FIELD_LOCK
import com.crowdproj.ad.backend.repository.gremlin.CwpAdGremlinConst.FIELD_OWNER_ID
import com.crowdproj.ad.backend.repository.gremlin.CwpAdGremlinConst.FIELD_PRODUCT_ID
import com.crowdproj.ad.backend.repository.gremlin.CwpAdGremlinConst.FIELD_TITLE
import com.crowdproj.ad.backend.repository.gremlin.CwpAdGremlinConst.FIELD_TMP_RESULT
import com.crowdproj.ad.backend.repository.gremlin.CwpAdGremlinConst.FIELD_VISIBILITY
import com.crowdproj.ad.backend.repository.gremlin.CwpAdGremlinConst.RESULT_SUCCESS
import com.crowdproj.ad.common.models.*
import exceptions.WrongEnumException
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.`__` as gr
import org.apache.tinkerpop.gremlin.structure.Vertex
import org.apache.tinkerpop.gremlin.structure.VertexProperty

fun GraphTraversal<Vertex, Vertex>.addCwpAd(ad: CwpAd): GraphTraversal<Vertex, Vertex> =
    this
        .property(VertexProperty.Cardinality.single, FIELD_TITLE, ad.title.takeIf { it.isNotBlank() })
        .property(VertexProperty.Cardinality.single, FIELD_DESCRIPTION, ad.description.takeIf { it.isNotBlank() })
        .property(VertexProperty.Cardinality.single, FIELD_LOCK, ad.lock.takeIf { it != CwpAdLock.NONE }?.asString())
        .property(
            VertexProperty.Cardinality.single,
            FIELD_OWNER_ID,
            ad.ownerId.asString().takeIf { it.isNotBlank() }) // здесь можно сделать ссылку на объект владельца
        .property(VertexProperty.Cardinality.single, FIELD_AD_TYPE, ad.adType.takeIf { it != CwpAdDealSide.NONE }?.name)
        .property(
            VertexProperty.Cardinality.single,
            FIELD_VISIBILITY,
            ad.visibility.takeIf { it != CwpAdVisibility.NONE }?.name
        )
        .property(
            VertexProperty.Cardinality.single,
            FIELD_PRODUCT_ID,
            ad.productId.takeIf { it != CwpAdProductId.NONE }?.asString()
        )

fun GraphTraversal<Vertex, Vertex>.listCwpAd(result: String = RESULT_SUCCESS): GraphTraversal<Vertex, MutableMap<String, Any>> =
    project<Any?>(
        FIELD_ID,
        FIELD_OWNER_ID,
        FIELD_LOCK,
        FIELD_TITLE,
        FIELD_DESCRIPTION,
        FIELD_AD_TYPE,
        FIELD_VISIBILITY,
        FIELD_PRODUCT_ID,
        FIELD_TMP_RESULT,
    )
        .by(gr.id<Vertex>())
        .by(FIELD_OWNER_ID)
//        .by(gr.inE("Owns").outV().id())
        .by(FIELD_LOCK)
        .by(FIELD_TITLE)
        .by(FIELD_DESCRIPTION)
        .by(FIELD_AD_TYPE)
        .by(FIELD_VISIBILITY)
        .by(FIELD_PRODUCT_ID)
        .by(gr.constant(result))
//        .by(elementMap<Vertex, Map<Any?, Any?>>())

fun Map<String, Any?>.toCwpAd(): CwpAd = CwpAd(
    id = (this[FIELD_ID] as? String)?.let { CwpAdId(it) } ?: CwpAdId.NONE,
    ownerId = (this[FIELD_OWNER_ID] as? String)?.let { CwpAdUserId(it) } ?: CwpAdUserId.NONE,
    lock = (this[FIELD_LOCK] as? String)?.let { CwpAdLock(it) } ?: CwpAdLock.NONE,
    title = (this[FIELD_TITLE] as? String) ?: "",
    description = (this[FIELD_DESCRIPTION] as? String) ?: "",
    adType = when (val value = this[FIELD_AD_TYPE] as? String) {
        CwpAdDealSide.SUPPLY.name -> CwpAdDealSide.SUPPLY
        CwpAdDealSide.DEMAND.name -> CwpAdDealSide.DEMAND
        null -> CwpAdDealSide.NONE
        else -> throw WrongEnumException(
            "Cannot convert object from DB. " +
                    "adType = '$value' cannot be converted to ${CwpAdDealSide::class}"
        )
    },
    visibility = when (val value = this[FIELD_VISIBILITY]) {
        CwpAdVisibility.VISIBLE_PUBLIC.name -> CwpAdVisibility.VISIBLE_PUBLIC
        CwpAdVisibility.VISIBLE_TO_GROUP.name -> CwpAdVisibility.VISIBLE_TO_GROUP
        CwpAdVisibility.VISIBLE_TO_OWNER.name -> CwpAdVisibility.VISIBLE_TO_OWNER
        null -> CwpAdVisibility.NONE
        else -> throw WrongEnumException(
            "Cannot convert object from DB. " +
                    "visibility = '$value' cannot be converted to ${CwpAdVisibility::class}"
        )
    },
    productId = (this[FIELD_PRODUCT_ID] as? String)?.let { CwpAdProductId(it) } ?: CwpAdProductId.NONE,
)
