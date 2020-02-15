package api.v1.routes.filetypes.exceptions

import api.v1.exceptions.NotFoundException

/**
 * Thrown when a requested file type could not be found.
 */
class FileTypeNotFoundException(id: String) : NotFoundException(
    description = "File type not found",
    details = mapOf("id" to id)
)
