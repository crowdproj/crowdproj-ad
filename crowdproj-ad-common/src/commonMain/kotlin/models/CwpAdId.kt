package ru.otus.otuskotlin.marketplace.common.models

import kotlin.jvm.JvmInline

@JvmInline
value class CwpAdId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = CwpAdId("")
    }
}
