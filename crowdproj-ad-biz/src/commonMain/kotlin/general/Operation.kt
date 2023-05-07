package com.crowdproj.ad.biz.general

import com.crowdproj.ad.common.CwpAdContext
import com.crowdproj.ad.common.models.CwpAdCommand
import com.crowdproj.ad.common.models.CwpAdState
import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.chain

fun ICorAddExecDsl<CwpAdContext>.operation(title: String, command: CwpAdCommand, block: ICorAddExecDsl<CwpAdContext>.() -> Unit) = chain {
    block()
    this.title = title
    on { this.command == command && state == CwpAdState.RUNNING }
}
