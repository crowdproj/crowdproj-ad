package com.crowdproj.ad.common.helpers

import com.crowdproj.ad.common.models.CwpAdError

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

//fun CwpAdContext.addError(vararg error: CwpAdError) = errors.addAll(error)
//
//fun CwpAdContext.fail(error: CwpAdError) {
//    addError(error)
//    state = CwpAdState.FAILING
//}
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

