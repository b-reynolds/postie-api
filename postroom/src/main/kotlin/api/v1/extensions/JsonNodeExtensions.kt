package api.v1.extensions

import api.v1.exceptions.MissingFieldException
import com.fasterxml.jackson.databind.JsonNode

/**
 * Returns the value associated with the specified [fieldName] if it exits and is a [String].
 *
 * Otherwise, returns `null`.
 */
fun JsonNode.getStringOrNull(fieldName: String): String? {
    val value = get(fieldName)
        ?.takeIf { node -> node.isTextual }
        ?: return null

    return value.textValue()
}

/**
 * Returns the value associated with the specified [fieldName] if it exits and is a [String].
 *
 * Otherwise, throws a [MissingFieldException].
 */
fun JsonNode.getString(fieldName: String): String {
    return getStringOrNull(fieldName) ?: throw MissingFieldException(fieldName)
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
 * Returns the value associated with the specified [fieldName] if it exits and is a [Long].
 *
 * Otherwise, throws a [MissingFieldException].
 */
fun JsonNode.getLong(fieldName: String): Long {
    return getLongOrNull(fieldName) ?: throw MissingFieldException(fieldName)
}

/**
 * Returns the value associated with the specified [fieldName] if it exits and is a [Int].
 *
 * Otherwise, returns `null`.
 */
fun JsonNode.getIntOrNull(fieldName: String): Int? {
    val value = get(fieldName)
        ?.takeIf { node -> node.isNumber }
        ?: return null

    return value.intValue()
}

/**
 * Returns the value associated with the specified [fieldName] if it exits and is a [Int].
 *
 * Otherwise, throws a [MissingFieldException].
 */
fun JsonNode.getInt(fieldName: String): Int {
    return getIntOrNull(fieldName) ?: throw MissingFieldException(fieldName)
}
