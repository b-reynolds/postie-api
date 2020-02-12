package api.v1.exceptions

/**
 * Thrown when an incoming request request cannot be handled due to a client error.
 */
class FileNotFoundException(id: String) : NotFoundException(
    description = "File not found",
    details = mapOf("id" to id)
)
