package com.crowdproj.ad.biz.validation

import com.crowdproj.ad.common.CwpAdContext
import com.crowdproj.ad.common.models.CwpAdId
import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.ad.common.helpers.errorValidation
import com.crowdproj.ad.common.helpers.fail

fun ICorAddExecDsl<CwpAdContext>.validateIdProperFormat(title: String) = worker {
    this.title = title

    // Может быть вынесен в CwpAdId для реализации различных форматов
    val regExp = Regex("^[0-9a-zA-Z-]+$")
    on { adValidating.id != CwpAdId.NONE && ! adValidating.id.asString().matches(regExp) }
    handle {
        val encodedId = adValidating.id.asString()
            .replace("<", "&lt;")
            .replace(">", "&gt;")
        fail(
            errorValidation(
            field = "id",
            violationCode = "badFormat",
            description = "value $encodedId must contain only letters and numbers"
        )
        )
    }
}
