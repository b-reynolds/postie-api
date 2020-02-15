package api.v1.routes.auth.controllers

import api.helpers.Header
import api.v1.exceptions.UnauthorizedException
import io.javalin.http.Context

private const val AUTHENTICATION_TYPE = "Basic"

/**
 * Authentication controller.
 *
 * Verifies that requests contain the specified [apiKey] within their authorization header.
 */
class AuthenticationController(private val apiKey: String) {
    /**
     * Throws an [UnauthorizedException] if the request does not contain an authorization header
     * with the required [apiKey].
     */
    fun verify(context: Context) {
        if (getApiKey(context) != apiKey) {
            context.header(Header.WWW_AUTHENTICATE, AUTHENTICATION_TYPE)
            throw UnauthorizedException()
        }
    }

    private fun getApiKey(context: Context): String? {
        return context
            .header(Header.AUTHORIZATION)
            ?.substringAfter(AUTHENTICATION_TYPE)
            ?.trim()
    }
}
