package ru.otus.otuskotlin.marketplace.common.models

data class CwpError(
    val code: String = "",
    val group: String = "",
    val field: String = "",
    val message: String = "",
    val exception: Throwable? = null,
)
