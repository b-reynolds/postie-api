package api.v1.filetypes.controllers.dtos

import api.v1.extensions.getString
import api.v1.files.controllers.dtos.CreateFileDto
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonNode

/**
 * A custom deserializer that throws meaningful API exceptions if the
 * object could not be created.
 */
class CreateFileTypeDtoDeserializer : JsonDeserializer<CreateFileTypeDto>() {
    private fun JsonNode.asCreateFileTypeDto() = CreateFileTypeDto(
        name = getString(CreateFileDto.Fields.NAME)
    )

    override fun deserialize(jsonParser: JsonParser, context: DeserializationContext): CreateFileTypeDto {
        return jsonParser.codec
            .readTree<JsonNode>(jsonParser)
            .asCreateFileTypeDto()
    }
}
