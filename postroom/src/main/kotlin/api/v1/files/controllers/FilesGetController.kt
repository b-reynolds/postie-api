package api.v1.files.controllers

import api.v1.files.daos.FileDao
import io.javalin.http.Context
import io.javalin.http.NotFoundResponse

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
        val file = fileDao.get(fileId) ?: throw NotFoundResponse()

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
