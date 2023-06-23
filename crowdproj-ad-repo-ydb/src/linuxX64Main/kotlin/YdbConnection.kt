package com.crowdproj.ad.repo.ydb

import com.crowdproj.ad.repo.ydb.model.AdEntity
import com.crowdproj.ads.ydb.Init
import kotlinx.cinterop.cstr
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.toKString

actual class YdbConnection actual constructor(url: String) {

    actual fun save(entity: AdEntity) {
        val dsn = ""
        val token = ""
        val x = Init(dsn.cstr, token.cstr)
        val errorMsg = memScoped {
            x?.toKString()
        }
        println("ERROR: $errorMsg")
    }
}
