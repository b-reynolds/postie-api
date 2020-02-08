package api.v1.exceptions

/**
 * Thrown when a request is made using an invalid field (e.g. unsuitable type or value).
 */
class InvalidFieldException(fieldName: String, reason: String) : BadRequestException(
    description = "Invalid field",
    details = mapOf(
        "field" to fieldName,
        "error" to reason
    )
)
