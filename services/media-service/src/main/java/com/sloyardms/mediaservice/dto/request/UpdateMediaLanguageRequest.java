package com.sloyardms.mediaservice.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * This class is typically used for updating a Media entry and changing its language
 */
@Schema(
        name = "UpdateMediaLanguageRequest",
        description = "Request DTO for updating a media language."
)
@Data
public class UpdateMediaLanguageRequest {

    @Schema(description = "The ID of the language (required).", example = "1")
    @NotNull
    private Integer id;

    @Schema(description = "The media language name.", example = "English")
    private String name;

}
