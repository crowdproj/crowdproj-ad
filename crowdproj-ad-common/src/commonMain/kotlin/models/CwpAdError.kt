package com.crowdproj.ad.common.models

data class CwpAdError(
    val code: String = "",
    val group: String = "",
    val field: String = "",
    val message: String = "",
    val level: Level = Level.ERROR,
    val exception: Throwable? = null,
) {
    enum class Level { INFO, WARN, ERROR }
}
