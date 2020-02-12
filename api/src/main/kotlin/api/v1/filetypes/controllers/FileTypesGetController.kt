package api.v1.filetypes.controllers

import api.v1.filetypes.daos.FileTypesDao
import io.javalin.http.Context
import java.net.HttpURLConnection

/**
 * File types GET request controller.
 *
 * Handles the retrieval of file types.
 */
class FileTypesGetController(private val fileTypesDao: FileTypesDao) {
    /**
     * Responds to the request with a JSON array containing all available file types.
     */
    fun get(context: Context) {
        with(context) {
            status(HttpURLConnection.HTTP_OK)
            json(fileTypesDao.get())
        }
    }
}
