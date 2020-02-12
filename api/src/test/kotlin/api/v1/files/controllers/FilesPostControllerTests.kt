package api.v1.files.controllers

import api.v1.exceptions.InvalidFieldException
import api.v1.exceptions.MissingFieldException
import api.v1.files.controllers.dtos.CreateFileDto
import api.v1.files.daos.FileDao
import api.v1.files.daos.FileTypeDao
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.javalin.http.Context
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.net.HttpURLConnection

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class FilesPostControllerTests {
    private val objectMapper = jacksonObjectMapper()
    private val context = mockk<Context>(relaxed = true)

    private val fileDao = mockk<FileDao>(relaxed = true)
    private val fileTypeDao = mockk<FileTypeDao>(relaxed = true)

    private val fileTypeId = 1

    @Test
    fun `HTTP status code 204 is returned for a valid file`() {
        every { fileTypeDao.contains(fileTypeId) } returns true

        val dto = CreateFileDto("hello_world.txt", fileTypeId, "Hello, World!", null)
        every { context.body() } returns objectMapper.writeValueAsString(dto)

        FilesPostController(fileDao, fileTypeDao, objectMapper)
            .create(context)

        verify { context.status(HttpURLConnection.HTTP_CREATED) }
    }

    @Test
    fun `MissingFieldException is thrown if a file name is missing`() {
        val json = """{ "fileTypeId": 1, "contents": "Hello, World!" }"""
        every { context.body() } returns json

        Assertions.assertThrows(MissingFieldException::class.java) {
            FilesPostController(fileDao, fileTypeDao, objectMapper)
                .create(context)
        }
    }

    @Test
    fun `InvalidFieldException is thrown if a file name is not a String`() {
        every { fileTypeDao.contains(fileTypeId) } returns true

        val json = """{ "name": 1, "fileTypeId": 1, "contents": "Hello, World!" }"""
        every { context.body() } returns json

        Assertions.assertThrows(InvalidFieldException::class.java) {
            FilesPostController(fileDao, fileTypeDao, objectMapper)
                .create(context)
        }
    }

    @Test
    fun `InvalidFieldException is thrown if a file name is blank`() {
        every { fileTypeDao.contains(fileTypeId) } returns true

        val json = """{ "name": " ", "fileTypeId": 1, "contents": "Hello, World!" }"""
        every { context.body() } returns json

        Assertions.assertThrows(InvalidFieldException::class.java) {
            FilesPostController(fileDao, fileTypeDao, objectMapper)
                .create(context)
        }
    }

    @Test
    fun `InvalidFieldException is thrown if a file name is empty`() {
        every { fileTypeDao.contains(fileTypeId) } returns true

        val json = """{ "name": "", "fileTypeId": 1, "contents": "Hello, World!" }"""
        every { context.body() } returns json

        Assertions.assertThrows(InvalidFieldException::class.java) {
            FilesPostController(fileDao, fileTypeDao, objectMapper)
                .create(context)
        }
    }

    @Test
    fun `MissingFieldException is thrown if a file type ID is missing`() {
        val json = """{ "name": "greeting.txt", "contents": "Hello, World!" }"""
        every { context.body() } returns json

        Assertions.assertThrows(MissingFieldException::class.java) {
            FilesPostController(fileDao, fileTypeDao, objectMapper)
                .create(context)
        }
    }

    @Test
    fun `InvalidFieldException is thrown if a file type ID is not an Int`() {
        val fileTypeId = 1
        every { fileTypeDao.contains(fileTypeId) } returns true

        val json = """{ "name": "greeting.txt", "fileTypeId": "1", "contents": "Hello, World!" }"""
        every { context.body() } returns json

        Assertions.assertThrows(InvalidFieldException::class.java) {
            FilesPostController(fileDao, fileTypeDao, objectMapper)
                .create(context)
        }
    }

    @Test
    fun `InvalidFieldException is thrown if a file type ID does not exist`() {
        every { fileTypeDao.contains(fileTypeId) } returns false

        val json = """{ "name": "greeting.txt", "fileTypeId": $fileTypeId, "contents": "Hello, World!" }"""
        every { context.body() } returns json

        Assertions.assertThrows(InvalidFieldException::class.java) {
            FilesPostController(fileDao, fileTypeDao, objectMapper)
                .create(context)
        }
    }

    @Test
    fun `MissingFieldException is thrown if a file contents is missing`() {
        val json = """{ "name": "greeting.txt", "fileTypeId": 1 }"""
        every { context.body() } returns json

        Assertions.assertThrows(MissingFieldException::class.java) {
            FilesPostController(fileDao, fileTypeDao, objectMapper)
                .create(context)
        }
    }

    @Test
    fun `InvalidFieldException is thrown if expiry date is in the past`() {
        every { fileTypeDao.contains(fileTypeId) } returns true

        val json =
            """{ "name": "greeting.txt", "fileTypeId": $fileTypeId, "contents": "Hello, World!", "expiresAt": 1 }"""
        every { context.body() } returns json

        Assertions.assertThrows(InvalidFieldException::class.java) {
            FilesPostController(fileDao, fileTypeDao, objectMapper)
                .create(context)
        }
    }
}
