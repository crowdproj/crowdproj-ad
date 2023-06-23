package com.crowdproj.ad.repo.ydb

import com.crowdproj.ad.repo.ydb.model.AdEntity

expect class YdbConnection(url: String) {
    fun save(entity: AdEntity)
}
