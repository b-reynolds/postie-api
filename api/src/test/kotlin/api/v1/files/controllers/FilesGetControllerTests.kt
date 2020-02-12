package api.v1.files.controllers

import api.v1.exceptions.FileNotFoundException
import api.v1.exceptions.InvalidFileIdException
import api.v1.files.daos.FilesDao
import io.javalin.http.Context
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.net.HttpURLConnection

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class FilesGetControllerTests {
    private val context = mockk<Context>(relaxed = true)
    private val dao = mockk<FilesDao>(relaxed = true)
    private val validFileId = "01bba99c-19fc-4b78-8a85-74ef9237a120"

    @Test
    fun `FileNotFoundException is thrown if a requested file does not exist`() {
        every { context.pathParam(FilesGetController.Parameter.FileId.name) } returns validFileId
        every { dao.get(validFileId) } returns null

        Assertions.assertThrows(FileNotFoundException::class.java) {
            FilesGetController(dao)
                .get(context)
        }
    }

    @Test
    fun `InvalidFileIdException is thrown if requested file ID is not a valid UUID`() {
        val invalidFileId = "01bba99c"

        every { context.pathParam(FilesGetController.Parameter.FileId.name) } returns invalidFileId

        Assertions.assertThrows(InvalidFileIdException::class.java) {
            FilesGetController(dao)
                .get(context)
        }
    }

    @Test
    fun `HTTP status code 200 is returned if a requested file exists`() {
        every { context.pathParam(FilesGetController.Parameter.FileId.name) } returns validFileId
        every { dao.get(validFileId) } returns mockk(relaxed = true)

        FilesGetController(dao)
            .get(context)

        verify { context.status(HttpURLConnection.HTTP_OK) }
    }
}
