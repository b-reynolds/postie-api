package api.v1.filetypes.controllers

import api.v1.files.controllers.dtos.CreateFileDto
import api.v1.filetypes.controllers.dtos.CreateFileTypeDto
import api.v1.filetypes.controllers.dtos.CreateFileTypeDtoDeserializer
import api.v1.filetypes.daos.FileTypesDao
import api.v1.utils.validateField
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.module.kotlin.readValue
import io.javalin.core.util.Header
import io.javalin.http.Context
import java.net.HttpURLConnection

/**
 * Files POST request controller.
 *
 * Handles the creation of new file types.
 */
class FileTypesPostController(
    private val fileTypesDao: FileTypesDao,
    objectMapper: ObjectMapper
) {
    private val objectMapper = objectMapper.registerModule(
        SimpleModule()
            .addDeserializer(CreateFileTypeDto::class.java, CreateFileTypeDtoDeserializer())
    )

    /**
     * Creates a new file type.
     *
     * Upon success, responds with a 201 created status code and includes a location header leading to the newly
     * created resource.
     */
    fun create(context: Context) {
        val dto = objectMapper
            .readValue<CreateFileTypeDto>(context.body())
            .validate()

        val name = dto.name.trim().toLowerCase()

        val fileType = fileTypesDao.get(name) ?: fileTypesDao.insert(name)

        with(context) {
            header(Header.LOCATION, "${context.host()}${context.path()}${fileType.id}")
            status(HttpURLConnection.HTTP_CREATED)
        }
    }

    private fun CreateFileTypeDto.validate(): CreateFileTypeDto {
        validateField(CreateFileDto.Fields.NAME, "Cannot be blank") { dto ->
            dto.name.isNotBlank()
        }

        return this
    }
}
