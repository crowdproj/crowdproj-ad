package com.crowdproj.ad.biz.validation

import com.crowdproj.ad.common.CwpAdContext
import com.crowdproj.ad.common.models.CwpAdState
import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.chain

fun ICorAddExecDsl<CwpAdContext>.validation(block: ICorAddExecDsl<CwpAdContext>.() -> Unit) = chain {
    block()
    title = "Валидация"

    on { state == CwpAdState.RUNNING }
}
