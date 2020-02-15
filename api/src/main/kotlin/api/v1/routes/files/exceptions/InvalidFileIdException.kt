package api.v1.routes.files.exceptions

import api.v1.exceptions.BadRequestException

/**
 * Thrown when a client makes a request using an invalid file ID.
 */
class InvalidFileIdException(id: String) : BadRequestException(
    description = "Invalid file ID",
    details = mapOf("id" to id)
)
