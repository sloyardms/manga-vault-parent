package com.sloyardms.mediaservice.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Schema(
        name = "CreateMediaCharacterRequest",
        description = "Request DTO for creating a media character."
)
@Data
public class CreateMediaCharacterRequest {

    @Schema(description = "The name of the character.", example = "Kakashi Hatake")
    @NotBlank
    private String name;

}
