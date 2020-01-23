package files.controllers

import files.daos.SatchelFileDao
import files.daos.SatchelFileTypeDao
import io.javalin.http.Context
import io.javalin.http.NotFoundResponse
import org.koin.core.KoinComponent
import org.koin.core.get
import java.sql.Timestamp
import java.time.Instant

/**
 * Handles routes relating to the creation/management of files.
 */
class FilesController(
    private val satchelFileDao: SatchelFileDao,
    private val satchelFileTypeDao: SatchelFileTypeDao
) : KoinComponent {
    /**
     * Responds to the request with a JSON object representing the file associated with the specified ID. If no such
     * file exists a HTTP status 404 response will be sent.
     */
    fun get(context: Context) {
        val fileId = context.pathParam(Parameters.FILE_ID)
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
        val dto = context.bodyValidator<CreateDao>()
            .check({ dto -> dto.name.isNotBlank() })
            .check({ dto -> dto.fileTypeId > 0 && satchelFileTypeDao.contains(dto.fileTypeId)})
            .check({ dto -> dto.contents.isNotBlank() })
            .check({ dto -> dto.expiresAt == null || dto.expiresAt.after(Timestamp.from(Instant.now()))})
            .get()

        context.json(satchelFileDao.insert(dto.name, dto.fileTypeId, dto.contents, dto.expiresAt))
    }

    /**
     * Parameters used by the controller.
     */
    object Parameters {
        const val FILE_ID = ":file-id"
    }
}