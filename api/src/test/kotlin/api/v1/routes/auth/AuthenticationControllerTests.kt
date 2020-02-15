package api.v1.routes.auth

import api.helpers.Header
import api.v1.exceptions.UnauthorizedException
import api.v1.routes.auth.controllers.AuthenticationController
import io.javalin.http.Context
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class AuthenticationControllerTests {
    private val apiKey = "secret-key"
    private val context = mockk<Context>(relaxed = true)

    @Test
    fun `Authorized requests are permitted`() {
        every { context.header(Header.AUTHORIZATION) } returns apiKey

        AuthenticationController(apiKey)
            .verify(context)
    }

    @Test
    fun `UnauthorizedException is thrown for unauthorized requests`() {
        every { context.header(Header.AUTHORIZATION) } returns null

        Assertions.assertThrows(UnauthorizedException::class.java) {
            AuthenticationController(apiKey)
                .verify(context)
        }
    }
}
