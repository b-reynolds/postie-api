package api.v1.exceptions

import java.net.HttpURLConnection

/**
 * Thrown when a client requests a resource that cannot not be found.
 */
open class NotFoundException(
    description: String = "Not Found",
    details: Map<String, String> = emptyMap()
) : ApiException(
    status = HttpURLConnection.HTTP_NOT_FOUND,
    description = description,
    details = details
)
