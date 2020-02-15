package api.v1.utils.extensions

import api.v1.exceptions.InvalidFieldException
import api.v1.exceptions.MissingFieldException
import com.fasterxml.jackson.databind.JsonNode

/**
 * Returns the value associated with the specified [fieldName].
 *
 * If the value does not exist a [MissingFieldException]. If the value exists but is not a [String] an
 * [InvalidFieldException] will be thrown.
 */
fun JsonNode.getString(fieldName: String): String {
    val value = get(fieldName) ?: throw MissingFieldException(fieldName)

    if (!value.isTextual) {
        throw InvalidFieldException(fieldName, "Not a valid String")
    }

    return value.textValue()
}

/**
 * Returns the value associated with the specified [fieldName] if it exits and is a [Long].
 *
 * Otherwise, returns `null`.
 */
fun JsonNode.getLongOrNull(fieldName: String): Long? {
    val value = get(fieldName)
        ?.takeIf { node -> node.isNumber }
        ?: return null

    return value.longValue()
}

/**
 * Returns the value associated with the specified [fieldName] if it exits and is a [Int].
 *
 * If the value does not exist a [MissingFieldException]. If the value exists but is not a [Int] an
 * [InvalidFieldException] will be thrown.
 */
fun JsonNode.getInt(fieldName: String): Int {
    val value = get(fieldName) ?: throw MissingFieldException(fieldName)

    if (!value.isNumber) {
        throw InvalidFieldException(fieldName, "Not a valid Int")
    }

    return value.intValue()
}
