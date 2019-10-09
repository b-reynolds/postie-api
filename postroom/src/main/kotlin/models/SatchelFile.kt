package models

import java.sql.Timestamp

/**
 * Models a record within the `files` table of the database.
 *
 * @property id Unique ID of the file.
 * @property name Name of the file.
 * @property fileTypeId Unique ID of the file's type.
 * @property contents Contents of the file.
 * @property expired Whether or not the file has expired (see [expiresAt]). Expired files should not be served.
 * @property createdAt Date/time at which the file was created.
 * @property expiresAt Date/time at which the file will expire (optional).
 */
data class SatchelFile(
    val id: Int,
    val name: String,
    val fileTypeId: Int,
    val contents: String,
    val expired: Boolean,
    val createdAt: Timestamp,
    val expiresAt: Timestamp?
)