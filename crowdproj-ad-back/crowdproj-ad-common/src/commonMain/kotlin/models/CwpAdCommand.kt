package com.crowdproj.ad.common.models

enum class CwpAdCommand(val isUpdatable: Boolean) {
    NONE(isUpdatable = false),
    CREATE(isUpdatable = true),
    READ(isUpdatable = false),
    UPDATE(isUpdatable = true),
    DELETE(isUpdatable = true),
    SEARCH(isUpdatable = false),
    OFFERS(isUpdatable = false),
    ;
}
