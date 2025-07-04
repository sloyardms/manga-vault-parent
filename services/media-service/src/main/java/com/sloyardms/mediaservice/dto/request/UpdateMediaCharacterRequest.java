package com.sloyardms.mediaservice.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(
        name = "UpdateMediaCharacterRequest",
        description = "Request DTO for updating a media character."
)
@Data
public class UpdateMediaCharacterRequest {

    @Schema(description = "The ID of the character. If null, a new entry will be created using the name field", example = "1")
    private Integer id;

    @Schema(description = "The new name of the character.", example = "Kakashi Hatake")
    private String name;

}
