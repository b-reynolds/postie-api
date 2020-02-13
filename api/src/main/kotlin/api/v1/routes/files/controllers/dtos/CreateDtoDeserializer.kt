package api.v1.routes.files.controllers.dtos

import api.v1.utils.extensions.getInt
import api.v1.utils.extensions.getLongOrNull
import api.v1.utils.extensions.getString
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonNode
import java.sql.Timestamp

/**
 * A custom deserializer that throws meaningful API exceptions if the
 * object could not be created.
 */
class CreateDtoDeserializer : JsonDeserializer<CreateFileDto>() {
    private fun JsonNode.asCreateDao() = CreateFileDto(
        name = getString(CreateFileDto.Fields.NAME),
        fileTypeId = getInt(CreateFileDto.Fields.FILE_TYPE_ID),
        contents = getString(CreateFileDto.Fields.CONTENTS),
        expiresAt = getLongOrNull(CreateFileDto.Fields.EXPIRES_AT)
            ?.let { expiresAt -> Timestamp(expiresAt) }
    )

    override fun deserialize(jsonParser: JsonParser, context: DeserializationContext): CreateFileDto {
        return jsonParser.codec
            .readTree<JsonNode>(jsonParser)
            .asCreateDao()
    }
}
