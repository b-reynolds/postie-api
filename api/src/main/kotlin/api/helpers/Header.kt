package api.helpers

/**
 * HTTP headers let the client and the server pass additional information with an HTTP request or response.
 */
object Header {
    /**
     * The HTTP Authorization request header contains the credentials to authenticate a user agent with a server,
     * usually after the server has responded with a 401 Unauthorized status and the WWW-Authenticate header.
     */
    const val AUTHORIZATION = "Authorization"

    /**
     * The HTTP WWW-Authenticate response header defines the authentication method that should be used to gain access
     * to a resource.
     */
    const val WWW_AUTHENTICATE = "WWW-Authenticate"
}
