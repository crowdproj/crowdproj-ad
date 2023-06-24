package com.crowdproj.ad.repo.ydb

import com.crowdproj.ad.repo.ydb.model.AdEntity
import com.crowdproj.ads.ydb.Init
import kotlinx.cinterop.*
import platform.linux.free

actual class YdbConnection actual constructor(url: String) {

    actual fun save(entity: AdEntity) {
        val dsn = ""
        val token = ""
        val res = Init(dsn.cstr, token.cstr)
        val (errorMsg: String?, cntMsg: String?) = res.useContents {
            Pair(r0?.toKString(), r1?.toKString()).also {
                free(r0)
                free(r1)
            }
        }
        nativeHeap.free(res.objcPtr())
        println("ERROR: $errorMsg $cntMsg")
    }
}
