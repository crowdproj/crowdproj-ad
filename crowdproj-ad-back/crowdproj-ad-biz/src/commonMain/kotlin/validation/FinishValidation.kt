package com.crowdproj.ad.biz.validation

import com.crowdproj.ad.common.CwpAdContext
import com.crowdproj.ad.common.models.CwpAdState
import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker

fun ICorAddExecDsl<CwpAdContext>.finishAdValidation(title: String) = worker {
    this.title = title
    on { state == CwpAdState.RUNNING }
    handle {
        adValidated = adValidating
    }
}

fun ICorAddExecDsl<CwpAdContext>.finishAdFilterValidation(title: String) = worker {
    this.title = title
    on { state == CwpAdState.RUNNING }
    handle {
        adFilterValidated = adFilterValidating
    }
}
