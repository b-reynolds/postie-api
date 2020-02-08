package api.v1.exceptions

import java.net.HttpURLConnection

/**
 * Thrown when an unexpected error occurs.
 */
class InternalServerException(
    description: String = "Internal Server Error",
    details: Map<String, String> = emptyMap()
) : ApiException(
    status = HttpURLConnection.HTTP_INTERNAL_ERROR,
    description = description,
    details = details
)
