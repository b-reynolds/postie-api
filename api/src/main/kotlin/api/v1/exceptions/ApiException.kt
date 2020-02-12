package api.v1.exceptions

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

/**
 * An API related exception that should be thrown when an incoming request cannot be handled.
 *
 * @param status HTTP status code.
 * @param description Description of the error.
 * @param details Additional details.
 */
@JsonIgnoreProperties("stackTrace", "cause", "message", "localizedMessage", "suppressed")
open class ApiException(
    val status: Int,
    val description: String,
    val details: Map<String, String>
) : RuntimeException(description)
