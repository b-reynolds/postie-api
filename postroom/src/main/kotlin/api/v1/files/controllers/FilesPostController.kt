package api.v1.files.controllers

import api.v1.files.daos.FileDao
import api.v1.files.daos.FileTypeDao
import io.javalin.core.util.Header
import io.javalin.http.Context
import java.net.HttpURLConnection
import java.sql.Timestamp
import java.time.Instant

/**
 * Files POST request controller.
 *
 * Handles the creation of new files.
 */
class FilesPostController(
    private val fileDao: FileDao,
    private val fileTypeDao: FileTypeDao
) {
    /**
     * Creates a new file.
     *
     * Upon success, responds with a 201 created status code and includes a location header leading to the newly
     * created resource.
     */
    fun create(context: Context) {
        val dto = context.bodyValidator<CreateDao>()
            .check({ dto -> dto.name.isNotBlank() })
            .check({ dto -> dto.fileTypeId > 0 && fileTypeDao.contains(dto.fileTypeId) })
            .check({ dto -> dto.contents.isNotBlank() })
            .check({ dto -> dto.expiresAt == null || dto.expiresAt.after(Timestamp.from(Instant.now())) })
            .get()

        val file = fileDao.insert(dto.name, dto.fileTypeId, dto.contents, dto.expiresAt)

        with(context) {
            header(Header.LOCATION, "${context.host()}${context.path()}${file.id}")
            status(HttpURLConnection.HTTP_CREATED)
        }
    }

    private data class CreateDao(
        val name: String,
        val fileTypeId: Int,
        val contents: String,
        val expiresAt: Timestamp?
    )
}
