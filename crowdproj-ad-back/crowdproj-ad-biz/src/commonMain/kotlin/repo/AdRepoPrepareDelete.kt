package com.crowdproj.ad.biz.repo

import com.crowdproj.ad.common.CwpAdContext
import com.crowdproj.ad.common.models.CwpAdState
import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker

fun ICorAddExecDsl<CwpAdContext>.repoPrepareDelete(title: String) = worker {
    this.title = title
    description = """
        Готовим данные к удалению из БД
    """.trimIndent()
    on { state == CwpAdState.RUNNING }
    handle {
        adRepoPrepare = adValidated.deepCopy()
    }
}
