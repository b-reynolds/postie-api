package api.v1.routes.filetypes.controllers

import api.v1.exceptions.InvalidFieldException
import api.v1.exceptions.MissingFieldException
import api.v1.routes.filetypes.controllers.dtos.CreateFileTypeDto
import api.v1.routes.filetypes.controllers.dtos.CreateFileTypeDtoDeserializer
import database.daos.FileTypesDao
import database.models.FileType
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.javalin.http.Context
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.net.HttpURLConnection
import java.sql.Timestamp
import java.time.Instant

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class FileTypesPostControllerTests {
    private val context = mockk<Context>(relaxed = true)
    private val dao = mockk<FileTypesDao>(relaxed = true)
    private val objectMapper = jacksonObjectMapper().registerModule(
        SimpleModule()
            .addDeserializer(CreateFileTypeDto::class.java, CreateFileTypeDtoDeserializer())
    )

    @Test
    fun `HTTP status code 204 is returned when a new file type is created`() {
        val dto = CreateFileTypeDto("json")

        every { dao.get(dto.name) } returns null
        every { context.body() } returns objectMapper.writeValueAsString(dto)

        FileTypesPostController(dao, objectMapper)
            .create(context)

        verify { context.status(HttpURLConnection.HTTP_CREATED) }
    }

    @Test
    fun `HTTP status code 204 is returned when a matching file type is returned`() {
        val dto = CreateFileTypeDto("json")

        every { dao.get(dto.name) } returns FileType(
            1,
            dto.name,
            Timestamp.from(Instant.now())
        )
        every { context.body() } returns objectMapper.writeValueAsString(dto)

        FileTypesPostController(dao, objectMapper)
            .create(context)

        verify { context.status(HttpURLConnection.HTTP_CREATED) }
    }

    @Test
    fun `MissingFieldException is thrown if file type name is missing`() {
        val json = """{ }"""
        every { context.body() } returns json

        Assertions.assertThrows(MissingFieldException::class.java) {
            FileTypesPostController(dao, objectMapper)
                .create(context)
        }
    }

    @Test
    fun `InvalidFieldException is thrown if file type name is blank`() {
        val json = """{ "name": " " }"""
        every { context.body() } returns json

        Assertions.assertThrows(InvalidFieldException::class.java) {
            FileTypesPostController(dao, objectMapper)
                .create(context)
        }
    }
}
