package files.models

import java.sql.Timestamp

/**
 * Models a record within the `file_types` table of the database.
 *
 * @property id Unique ID of the file type.
 * @property name Name of the file type.
 * @property createdAt Date/time at which the file type was created.
 */
data class SatchelFileType(
    val id: Int,
    val name: String,
    val createdAt: Timestamp
)