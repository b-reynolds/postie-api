package api.v1.utils

import api.v1.exceptions.InvalidFieldException

/**
 * A helper method that can be used to perform validation on fields).
 *
 * If the supplied [condition] returns `false` an [InvalidFieldException] will be thrown that explains the reason for
 * failure (see [errorMessage]).
 */
inline fun <T> T.validateField(fieldName: String, errorMessage: String, condition: (T) -> Boolean): T {
    if (!condition(this)) {
        throw InvalidFieldException(fieldName, errorMessage)
    }

    return this
}
