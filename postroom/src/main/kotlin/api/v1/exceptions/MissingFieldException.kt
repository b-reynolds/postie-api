package api.v1.exceptions

/**
 * Thrown when a request is made that is missing a required field.
 */
class MissingFieldException(fieldName: String) : BadRequestException(
    description = "Missing field",
    details = mapOf("field" to fieldName)
)
