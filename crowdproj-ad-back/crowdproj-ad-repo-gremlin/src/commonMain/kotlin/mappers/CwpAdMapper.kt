package com.crowdproj.ad.backend.repo.gremlin.mappers

import com.crowdproj.ad.backend.repo.gremlin.CwpAdGremlinConst
import com.crowdproj.ad.backend.repo.gremlin.models.ArkadeResultCwpAd
import com.crowdproj.ad.common.models.*

const val PROPERTIES_SUBQUERY = "" +
        ".property(single, '${CwpAdGremlinConst.FIELD_TITLE}', ${CwpAdGremlinConst.FIELD_TITLE})" +
        ".property(single, '${CwpAdGremlinConst.FIELD_DESCRIPTION}', ${CwpAdGremlinConst.FIELD_DESCRIPTION})" +
        ".property(single, '${CwpAdGremlinConst.FIELD_LOCK}', ${CwpAdGremlinConst.FIELD_LOCK})" +
        ".property(single, '${CwpAdGremlinConst.FIELD_OWNER_ID}', ${CwpAdGremlinConst.FIELD_OWNER_ID})" +
        ".property(single, '${CwpAdGremlinConst.FIELD_AD_TYPE}', ${CwpAdGremlinConst.FIELD_AD_TYPE})" +
        ".property(single, '${CwpAdGremlinConst.FIELD_VISIBILITY}', ${CwpAdGremlinConst.FIELD_VISIBILITY})" +
        ".property(single, '${CwpAdGremlinConst.FIELD_PRODUCT_ID}', ${CwpAdGremlinConst.FIELD_PRODUCT_ID})" +
        ""
fun gremlinPrepareResp(mark: String = CwpAdGremlinConst.RESULT_SUCCESS) = ".project(" +
        "'${CwpAdGremlinConst.FIELD_ID}'," +
        "'${CwpAdGremlinConst.FIELD_OWNER_ID}'," +
        "'${CwpAdGremlinConst.FIELD_LOCK}'," +
        "'${CwpAdGremlinConst.FIELD_TITLE}'," +
        "'${CwpAdGremlinConst.FIELD_DESCRIPTION}'," +
        "'${CwpAdGremlinConst.FIELD_AD_TYPE}'," +
        "'${CwpAdGremlinConst.FIELD_VISIBILITY}'," +
        "'${CwpAdGremlinConst.FIELD_PRODUCT_ID}'," +
        "'${CwpAdGremlinConst.FIELD_TMP_RESULT}')" +
        ".by(id())" +
        ".by('${CwpAdGremlinConst.FIELD_OWNER_ID}')" +
        ".by('${CwpAdGremlinConst.FIELD_LOCK}')" +
        ".by('${CwpAdGremlinConst.FIELD_TITLE}')" +
        ".by('${CwpAdGremlinConst.FIELD_DESCRIPTION}')" +
        ".by('${CwpAdGremlinConst.FIELD_AD_TYPE}')" +
        ".by('${CwpAdGremlinConst.FIELD_VISIBILITY}')" +
        ".by('${CwpAdGremlinConst.FIELD_PRODUCT_ID}')" +
        ".by(constant('$mark'))"

fun ArkadeResultCwpAd.toCommon(): CwpAd = CwpAd(
    id = id?.let { CwpAdId(it) } ?: CwpAdId.NONE,
    lock = lock?.let { CwpAdLock(it) } ?: CwpAdLock.NONE,
    title = title ?: "",
    description = description ?: "",
    ownerId = ownerId?.let { CwpAdUserId(it) } ?: CwpAdUserId.NONE,
    adType = adType.dealSideFromDb(),
    visibility = visibility.visibilityFromDb(),
    productId = productId?.let { CwpAdProductId(it) } ?: CwpAdProductId.NONE,
)

fun CwpAd.toParams() = mapOf(
    CwpAdGremlinConst.FIELD_TITLE to title,
    CwpAdGremlinConst.FIELD_DESCRIPTION to description,
    CwpAdGremlinConst.FIELD_LOCK to lock.asString(),
    CwpAdGremlinConst.FIELD_OWNER_ID to ownerId.asString(),
    CwpAdGremlinConst.FIELD_AD_TYPE to adType.toDb(),
    CwpAdGremlinConst.FIELD_VISIBILITY to visibility.toDb(),
    CwpAdGremlinConst.FIELD_PRODUCT_ID to productId.asString(),
)
