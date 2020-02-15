package api.v1.exceptions

import java.net.HttpURLConnection

/**
 * Thrown when an incoming request request cannot be handled due to an authentication error.
 */
open class UnauthorizedException(
    description: String = "Unauthorized",
    details: Map<String, String> = emptyMap()
) : ApiException(
    status = HttpURLConnection.HTTP_UNAUTHORIZED,
    description = description,
    details = details
)
