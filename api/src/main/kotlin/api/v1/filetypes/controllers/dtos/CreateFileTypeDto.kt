package api.v1.filetypes.controllers.dtos

/**
 * Models the JSON object required to create new file types.
 */
data class CreateFileTypeDto(val name: String) {
    /**
     * Field names, used for validation/to provide meaningful error messages.
     */
    object Fields {
        const val NAME = "name"
    }
}
