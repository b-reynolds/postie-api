package api.v1.filetypes.controllers

import api.v1.exceptions.FileTypeNotFoundException
import api.v1.exceptions.InvalidFileTypeIdException
import api.v1.filetypes.daos.FileTypesDao
import io.javalin.http.Context
import org.koin.ext.isInt
import java.net.HttpURLConnection

/**
 * File type GET request controller.
 *
 * Handles the retrieval of file types.
 */
class FileTypeGetController(private val fileTypesDao: FileTypesDao) {
    /**
     * Responds to the request with a JSON object representing the file type associated with the specified ID. If no
     * such file type exists a HTTP status 404 response will be sent.
     */
    fun get(context: Context) {
        val fileTypeId = context.pathParam(Parameter.FileTypeId.name)
        if (!fileTypeId.isInt()) {
            throw InvalidFileTypeIdException(fileTypeId)
        }

        val fileType = fileTypesDao.get(fileTypeId.toInt()) ?: throw FileTypeNotFoundException(fileTypeId)

        with(context) {
            status(HttpURLConnection.HTTP_OK)
            json(fileType)
        }
    }

    /**
     * Request parameters.
     */
    sealed class Parameter(val name: String) {
        /**
         * File type ID.
         */
        object FileTypeId : Parameter(":file-type-id")
    }
}
