package api.v1.exceptions

import java.net.HttpURLConnection

/**
 * Thrown when an incoming request request cannot be handled due to a client error.
 */
open class BadRequestException(
    description: String = "Bad Request",
    details: Map<String, String> = emptyMap()
) : ApiException(
    status = HttpURLConnection.HTTP_BAD_REQUEST,
    description = description,
    details = details
)
