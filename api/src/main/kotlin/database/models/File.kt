package database.models

import java.sql.Timestamp

/**
 * Models a record within the `files` table of the database.
 *
 * @property id Unique ID of the file.
 * @property name Name of the file.
 * @property fileTypeId Unique ID of the file's type.
 * @property contents Contents of the file.
 * @property createdAt Date/time at which the file was created.
 * @property expiresAt Date/time at which the file will expire (optional).
 */
data class File(
    val id: String,
    val name: String,
    val fileTypeId: Int?,
    val contents: String,
    val createdAt: Timestamp,
    val expiresAt: Timestamp?
)
