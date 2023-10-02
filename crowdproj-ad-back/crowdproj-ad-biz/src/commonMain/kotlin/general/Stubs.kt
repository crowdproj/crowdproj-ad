package com.crowdproj.ad.biz.general

import com.crowdproj.ad.common.CwpAdContext
import com.crowdproj.ad.common.models.CwpAdState
import com.crowdproj.ad.common.models.CwpAdWorkMode
import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.chain

fun ICorAddExecDsl<CwpAdContext>.stubs(title: String, block: ICorAddExecDsl<CwpAdContext>.() -> Unit) = chain {
    block()
    this.title = title
    on { workMode == CwpAdWorkMode.STUB && state == CwpAdState.RUNNING }
}
