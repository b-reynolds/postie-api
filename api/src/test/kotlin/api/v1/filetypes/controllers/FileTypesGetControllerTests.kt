package api.v1.filetypes.controllers

import api.v1.filetypes.daos.FileTypesDao
import api.v1.filetypes.models.FileType
import io.javalin.http.Context
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.net.HttpURLConnection
import java.sql.Timestamp
import java.time.Instant

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class FileTypesGetControllerTests {
    private val context = mockk<Context>(relaxed = true)
    private val dao = mockk<FileTypesDao>(relaxed = true)

    @Test
    fun `HTTP status code 200 is returned if no file types exist`() {
        every { dao.get() } returns emptyList()

        FileTypesGetController(dao)
            .get(context)

        verify { context.status(HttpURLConnection.HTTP_OK) }
    }

    @Test
    fun `Empty list is returned if no file types exist`() {
        every { dao.get() } returns emptyList()

        FileTypesGetController(dao)
            .get(context)

        verify { context.json(emptyList<FileType>()) }
    }

    @Test
    fun `Supported file types are correctly returned`() {
        val fileTypes = listOf(FileType(1, "text", Timestamp.from(Instant.now())))

        every { dao.get() } returns fileTypes

        FileTypesGetController(dao)
            .get(context)

        verify { context.json(fileTypes) }
    }

    @Test
    fun `HTTP status code 200 is returned if file types exist`() {
        val fileTypes = listOf(FileType(1, "text", Timestamp.from(Instant.now())))

        every { dao.get() } returns fileTypes

        FileTypesGetController(dao)
            .get(context)

        verify { context.status(HttpURLConnection.HTTP_OK) }
    }
}
