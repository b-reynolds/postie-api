package api.v1.exceptions

/**
 * Thrown when a requested file type could not be found.
 */
class FileTypeNotFoundException(id: String) : NotFoundException(
    description = "File type not found",
    details = mapOf("id" to id)
)
