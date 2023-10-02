package com.crowdproj.ad.common.helpers

import com.crowdproj.ad.common.CwpAdContext
import com.crowdproj.ad.common.exceptions.RepoConcurrencyException
import com.crowdproj.ad.common.models.CwpAdError
import com.crowdproj.ad.common.models.CwpAdLock
import com.crowdproj.ad.common.models.CwpAdState

fun Throwable.asCwpAdError(
    code: String = "unknown",
    group: String = "exceptions",
    message: String = this.message ?: "",
) = CwpAdError(
    code = code,
    group = group,
    field = "",
    message = message,
    exception = this,
)

fun CwpAdContext.addError(vararg error: CwpAdError) = errors.addAll(error)

fun CwpAdContext.fail(error: CwpAdError) {
    addError(error)
    state = CwpAdState.FAILING
}
fun errorValidation(
    field: String,
    /**
     * Код, характеризующий ошибку. Не должен включать имя поля или указание на валидацию.
     * Например: empty, badSymbols, tooLong, etc
     */
    violationCode: String,
    description: String,
    level: CwpAdError.Level = CwpAdError.Level.ERROR,
) = CwpAdError(
    code = "validation-$field-$violationCode",
    field = field,
    group = "validation",
    message = "Validation error for field $field: $description",
    level = level,
)

fun errorAdministration(
    /**
     * Код, характеризующий ошибку. Не должен включать имя поля или указание на валидацию.
     * Например: empty, badSymbols, tooLong, etc
     */
    field: String = "",
    violationCode: String,
    description: String,
    level: CwpAdError.Level = CwpAdError.Level.ERROR,
) = CwpAdError(
    field = field,
    code = "administration-$violationCode",
    group = "administration",
    message = "Microservice management error: $description",
    level = level,
)

fun errorRepoConcurrency(
    expectedLock: CwpAdLock,
    actualLock: CwpAdLock?,
    exception: Exception? = null,
) = CwpAdError(
    field = "lock",
    code = "concurrency",
    group = "repo",
    message = "The object has been changed concurrently by another user or process",
    exception = exception ?: RepoConcurrencyException(expectedLock, actualLock),
)

