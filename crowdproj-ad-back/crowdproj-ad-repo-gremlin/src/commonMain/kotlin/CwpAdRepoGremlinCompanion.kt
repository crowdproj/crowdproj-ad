package com.crowdproj.ad.backend.repo.gremlin

import com.crowdproj.ad.backend.repo.gremlin.exceptions.DbDuplicatedElementsException
import com.crowdproj.ad.common.helpers.errorAdministration
import com.crowdproj.ad.common.models.CwpAdError
import com.crowdproj.ad.common.repo.DbAdResponse

open class CwpAdRepoGremlinCompanion {
    val resultErrorEmptyId = DbAdResponse(
        data = null,
        isSuccess = false,
        errors = listOf(
            CwpAdError(
                field = "id",
                message = "Id must not be null or blank"
            )
        )
    )
    val resultErrorEmptyLock = DbAdResponse(
        data = null,
        isSuccess = false,
        errors = listOf(
            CwpAdError(
                field = "lock",
                message = "Lock must be provided"
            )
        )
    )

    fun resultErrorNotFound(key: String, e: Throwable? = null) = DbAdResponse(
        isSuccess = false,
        data = null,
        errors = listOf(
            CwpAdError(
                code = "not-found",
                field = "id",
                message = "Not Found object with key $key",
                exception = e
            )
        )
    )

    fun errorDuplication(key: String) = DbAdResponse(
        data = null,
        isSuccess = false,
        errors = listOf(
            errorAdministration(
                violationCode = "duplicateObjects",
                description = "Database consistency failure",
                exception = DbDuplicatedElementsException("Db contains multiple elements for id = '$key'")
            )
        )
    )
}
