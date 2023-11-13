package ru.otus.otuskotlin.marketplace.backend.repository.gremlin.mappers

import com.crowdproj.ad.common.models.CwpAd

fun CwpAd.label(): String? = this::class.simpleName
