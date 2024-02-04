package com.crowdproj.ad.repo.ydb

import com.crowdproj.ad.repo.ydb.model.AdEntity

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class YdbConnection(url: String) {
    fun save(entity: AdEntity)
}
