package api.v1.exceptions

/**
 * Thrown when a client makes a request using an invalid file type ID.
 */
class InvalidFileTypeIdException(id: String) : BadRequestException(
    description = "Invalid file type ID",
    details = mapOf("id" to id)
)
