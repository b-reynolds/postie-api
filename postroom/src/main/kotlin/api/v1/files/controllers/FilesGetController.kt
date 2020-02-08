package api.v1.files.controllers

import api.v1.exceptions.FileNotFoundException
import api.v1.exceptions.InvalidFileIdException
import api.v1.files.daos.FileDao
import io.javalin.http.Context

/**
 * Files GET request controller.
 *
 * Handles the retrieval of files.
 */
class FilesGetController(private val fileDao: FileDao) {
    /**
     * Responds to the request with a JSON object representing the file associated with the specified ID. If no such
     * file exists a HTTP status 404 response will be sent.
     */
    fun get(context: Context) {
        val fileId = context.pathParam(Parameter.FileId.name)
        if (!isValidId(fileId)) {
            throw InvalidFileIdException(fileId)
        }

        val file = fileDao.get(fileId) ?: throw FileNotFoundException(fileId)

        context.json(file)
    }

    /**
     * Request parameters.
     */
    sealed class Parameter(val name: String) {
        /**
         * File ID.
         */
        object FileId : Parameter(":file-id")
    }
}

private val uuidRegex = Regex("""\b[0-9a-f]{8}\b-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-\b[0-9a-f]{12}\b""")

private fun isValidId(id: String) = id matches uuidRegex
