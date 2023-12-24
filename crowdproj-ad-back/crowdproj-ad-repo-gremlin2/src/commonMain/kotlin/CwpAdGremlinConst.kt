package com.crowdproj.ad.backend.repo.gremlin

object CwpAdGremlinConst {

    const val LABEL_AD = "AD"

    const val RESULT_SUCCESS = "success"
    const val RESULT_LOCK_FAILURE = "lock-failure"

    const val FIELD_ID = "#id"
    const val FIELD_TITLE = "title"
    const val FIELD_DESCRIPTION = "description"
    const val FIELD_AD_TYPE = "adType"
    const val FIELD_OWNER_ID = "ownerId"
    const val FIELD_VISIBILITY = "visibility"
    const val FIELD_PRODUCT_ID = "productId"
    const val FIELD_LOCK = "lock"
    const val FIELD_TMP_RESULT = "_result"

    const val FIELD_AD_TYPE_SUPPLY = "SUPPLY"
    const val FIELD_AD_TYPE_DEMAND = "DEMAND"
    const val FIELD_VISIBILITY_PUBLIC = "PUBLIC"
    const val FIELD_VISIBILITY_GROUP = "GROUP"
    const val FIELD_VISIBILITY_OWNER = "OWNER"
}
