package controllers.files

import daos.SatchelFileDao
import io.javalin.http.Context
import io.javalin.http.NotFoundResponse
import java.sql.Timestamp

/**
 * Handles routes relating to the creation/management of files.
 */
class FilesController(private val satchelFileDao: SatchelFileDao) {
    /**
     * Responds to the request with a JSON object representing the file associated with the specified ID. If no such
     * file exists a HTTP status 404 response will be sent.
     */
    fun get(context: Context) {
        val fileId = context.pathParam(Parameters.FILE_ID, Int::class.java).get()
        val file = satchelFileDao.get(fileId) ?: throw NotFoundResponse()

        context.json(file)
    }

    /**
     * DTO used in the creation of files (see [create]].
     *
     * @property name Name of the file.
     * @property fileTypeId Unique ID of the file's type.
     * @property contents Contents of the file.
     * @property expiresAt Date/time at which the file will expire (optional).
     */
    private data class CreateDao(
        val name: String,
        val fileTypeId: Int,
        val contents: String,
        val expiresAt: Timestamp?
    )

    /**
     * Creates a new file and responds with a JSON object representing it. If the request is malformed a HTTP status 400
     * will be sent.
     */
    fun create(context: Context) {
        with(context.body<CreateDao>()) {
            context.json(satchelFileDao.insert(name, fileTypeId, contents, expiresAt))
        }
    }

    /**
     * Parameters used by the controller.
     */
    object Parameters {
        const val FILE_ID = ":file-id"
    }
}