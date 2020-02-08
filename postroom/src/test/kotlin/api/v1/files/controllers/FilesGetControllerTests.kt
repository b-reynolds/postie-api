package api.v1.files.controllers

import api.v1.exceptions.FileNotFoundException
import api.v1.files.daos.FileDao
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
    private val dao = mockk<FileDao>(relaxed = true)
    private val fileId = "01bba99c-19fc-4b78-8a85-74ef9237a120"

    @Test
    fun `FileNotFoundException is thrown if a requested file does not exist`() {
        every { context.pathParam(FilesGetController.Parameter.FileId.name) } returns fileId
        every { dao.get(fileId) } returns null

        Assertions.assertThrows(FileNotFoundException::class.java) {
            FilesGetController(dao)
                .get(context)
        }
    }

    @Test
    fun `HTTP status code 200 is returned if a requested file exists`() {
        every { context.pathParam(FilesGetController.Parameter.FileId.name) } returns fileId
        every { dao.get(fileId) } returns mockk(relaxed = true)

        FilesGetController(dao)
            .get(context)

        verify { context.status(HttpURLConnection.HTTP_OK) }
    }
}
