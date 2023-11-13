package com.crowdproj.ad.backend.repository.gremlin.mappers

import com.crowdproj.ad.common.models.CwpAd

fun CwpAd.label(): String? = this::class.simpleName
