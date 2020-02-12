package api.v1.files.controllers

import api.v1.files.controllers.dtos.CreateFileDto
import api.v1.files.controllers.dtos.CreateDtoDeserializer
import api.v1.files.daos.FileDao
import api.v1.filetypes.daos.FileTypeDao
import api.v1.utils.validateField
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.module.kotlin.readValue
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
    private val fileTypeDao: FileTypeDao,
    objectMapper: ObjectMapper
) {
    private val objectMapper = objectMapper.registerModule(
        SimpleModule()
            .addDeserializer(CreateFileDto::class.java, CreateDtoDeserializer())
    )

    /**
     * Creates a new file.
     *
     * Upon success, responds with a 201 created status code and includes a location header leading to the newly
     * created resource.
     */
    fun create(context: Context) {
        val dto = objectMapper
            .readValue<CreateFileDto>(context.body())
            .validate()

        val file = fileDao.insert(dto.name, dto.fileTypeId, dto.contents, dto.expiresAt)

        with(context) {
            header(Header.LOCATION, "${context.host()}${context.path()}${file.id}")
            status(HttpURLConnection.HTTP_CREATED)
        }
    }

    private fun CreateFileDto.validate(): CreateFileDto {
        validateField(CreateFileDto.Fields.NAME, "Cannot be blank") { dto ->
            dto.name.isNotBlank()
        }

        validateField(CreateFileDto.Fields.FILE_TYPE_ID, "Must exist") { dto ->
            dto.fileTypeId > 0 && fileTypeDao.contains(dto.fileTypeId)
        }

        validateField(CreateFileDto.Fields.CONTENTS, "Cannot be blank") { dto ->
            dto.contents.isNotBlank()
        }

        validateField(CreateFileDto.Fields.EXPIRES_AT, "Cannot be in the past") { dto ->
            dto.expiresAt == null || dto.expiresAt.after(Timestamp.from(Instant.now()))
        }

        return this
    }
}
