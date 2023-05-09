package com.crowdproj.ad.biz.repo

import com.crowdproj.ad.common.CwpAdContext
import com.crowdproj.ad.common.models.CwpAdState
import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker

fun ICorAddExecDsl<CwpAdContext>.repoPrepareOffers(title: String) = worker {
    this.title = title
    description = "Готовим данные к поиску предложений в БД"
    on { state == CwpAdState.RUNNING }
    handle {
        adRepoPrepare = adRepoRead.deepCopy()
        adRepoDone = adRepoRead.deepCopy()
    }
}
