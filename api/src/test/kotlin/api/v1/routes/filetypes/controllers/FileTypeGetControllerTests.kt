package api.v1.routes.filetypes.controllers

import api.v1.routes.filetypes.exceptions.FileTypeNotFoundException
import api.v1.routes.filetypes.exceptions.InvalidFileTypeIdException
import database.daos.FileTypesDao
import database.models.FileType
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
class FileTypeGetControllerTests {
    private val context = mockk<Context>(relaxed = true)
    private val dao = mockk<FileTypesDao>(relaxed = true)

    @Test
    fun `HTTP status code 200 is returned if a requested file type exists`() {
        val fileType = FileType(1, "text", Timestamp.from(Instant.now()))

        every { context.pathParam(FileTypeGetController.Parameter.FileTypeId.name) } returns fileType.id.toString()
        every { dao.get(fileType.id) } returns fileType

        FileTypeGetController(dao)
            .get(context)

        verify { context.status(HttpURLConnection.HTTP_OK) }
    }

    @Test
    fun `Existing File types are returned correctly`() {
        val fileType = FileType(1, "text", Timestamp.from(Instant.now()))

        every { context.pathParam(FileTypeGetController.Parameter.FileTypeId.name) } returns fileType.id.toString()
        every { dao.get(fileType.id) } returns fileType

        FileTypeGetController(dao)
            .get(context)

        verify { context.json(fileType) }
    }

    @Test
    fun `HTTP status code 404 is returned if a requested file type does not exist`() {
        val fileTypeId = 1
        every { context.pathParam(FileTypeGetController.Parameter.FileTypeId.name) } returns fileTypeId.toString()
        every { dao.get(fileTypeId) } returns null

        Assertions.assertThrows(FileTypeNotFoundException::class.java) {
            FileTypeGetController(dao)
                .get(context)
        }
    }

    @Test
    fun `FileTypeNotFoundException is thrown if a requested file type does not exist`() {
        val fileTypeId = 1
        every { context.pathParam(FileTypeGetController.Parameter.FileTypeId.name) } returns fileTypeId.toString()
        every { dao.get(fileTypeId) } returns null

        Assertions.assertThrows(FileTypeNotFoundException::class.java) {
            FileTypeGetController(dao)
                .get(context)
        }
    }

    @Test
    fun `InvalidFileTypeId is thrown if requests contain an invalid file type ID`() {
        every { context.pathParam(FileTypeGetController.Parameter.FileTypeId.name) } returns "abc"

        Assertions.assertThrows(InvalidFileTypeIdException::class.java) {
            FileTypeGetController(dao)
                .get(context)
        }
    }
}
