package api.v1.routes.files.controllers.dtos

import java.sql.Timestamp

/**
 * Models the JSON object required to create new files.
 */
data class CreateFileDto(
    val name: String,
    val fileTypeId: Int?,
    val contents: String,
    val expiresAt: Timestamp?
) {
    /**
     * Field names, used for validation/to provide meaningful error messages.
     */
    object Fields {
        const val NAME = "name"
        const val FILE_TYPE_ID = "fileTypeId"
        const val CONTENTS = "contents"
        const val EXPIRES_AT = "expiresAt"
    }
}

