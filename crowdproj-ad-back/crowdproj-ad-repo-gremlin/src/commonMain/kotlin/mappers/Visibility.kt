package com.crowdproj.ad.backend.repo.gremlin.mappers

import com.crowdproj.ad.backend.repo.gremlin.CwpAdGremlinConst
import com.crowdproj.ad.backend.repo.gremlin.exceptions.WrongEnumException
import com.crowdproj.ad.common.models.CwpAdVisibility

fun CwpAdVisibility.toDb(): String = when (this) {
    CwpAdVisibility.VISIBLE_PUBLIC -> CwpAdGremlinConst.FIELD_VISIBILITY_PUBLIC
    CwpAdVisibility.VISIBLE_TO_GROUP -> CwpAdGremlinConst.FIELD_VISIBILITY_GROUP
    CwpAdVisibility.VISIBLE_TO_OWNER -> CwpAdGremlinConst.FIELD_VISIBILITY_OWNER
    CwpAdVisibility.NONE -> ""
}

fun String?.visibilityFromDb(): CwpAdVisibility = when (this) {
    CwpAdGremlinConst.FIELD_VISIBILITY_PUBLIC -> CwpAdVisibility.VISIBLE_PUBLIC
    CwpAdGremlinConst.FIELD_VISIBILITY_GROUP -> CwpAdVisibility.VISIBLE_TO_GROUP
    CwpAdGremlinConst.FIELD_VISIBILITY_OWNER -> CwpAdVisibility.VISIBLE_TO_OWNER
    "" -> CwpAdVisibility.NONE
    else -> throw WrongEnumException("Not found value for visibility in DB: `$this`")
}
