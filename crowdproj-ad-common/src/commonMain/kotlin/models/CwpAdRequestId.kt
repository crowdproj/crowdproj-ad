package com.crowdproj.ad.common.models

import kotlin.jvm.JvmInline

@JvmInline
value class CwpAdRequestId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = CwpAdRequestId("")
    }
}
