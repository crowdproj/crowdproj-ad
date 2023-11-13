package com.crowdproj.ad.biz.general

import com.crowdproj.ad.common.CwpAdContext
import com.crowdproj.ad.common.models.CwpAdWorkMode
import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.ad.common.helpers.errorAdministration
import com.crowdproj.ad.common.helpers.fail
import com.crowdproj.ad.common.repo.IAdRepository

fun ICorAddExecDsl<CwpAdContext>.initRepo(title: String) = worker {
    this.title = title
    description = """
        Вычисление основного рабочего репозитория в зависимости от зпрошенного режима работы        
    """.trimIndent()
    handle {
        adRepo = when (workMode) {
            CwpAdWorkMode.TEST -> settings.repoTest
            CwpAdWorkMode.STUB -> settings.repoStub
            else -> settings.repoProd
        }
        if (workMode != CwpAdWorkMode.STUB && adRepo == IAdRepository.NONE) fail(
            errorAdministration(
                field = "repo",
                violationCode = "dbNotConfigured",
                description = "The database is unconfigured for chosen workmode ($workMode). " +
                        "Please, contact the administrator staff"
            )
        )
    }
}
