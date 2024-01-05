package com.crowdproj.ad.repo.ydb

import com.crowdproj.ad.repo.ydb.model.AdEntity

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class YdbConnection actual constructor(url: String) {
    actual fun save(entity: AdEntity) {
    }
}
