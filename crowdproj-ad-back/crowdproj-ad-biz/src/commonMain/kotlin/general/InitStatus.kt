package com.crowdproj.ad.biz.general

import com.crowdproj.ad.common.CwpAdContext
import com.crowdproj.ad.common.models.CwpAdState
import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker

fun ICorAddExecDsl<CwpAdContext>.initStatus(title: String) = worker() {
    this.title = title
    on { state == CwpAdState.NONE }
    handle { state = CwpAdState.RUNNING }
}
