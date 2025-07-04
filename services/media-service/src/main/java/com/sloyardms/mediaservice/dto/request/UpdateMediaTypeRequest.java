package com.sloyardms.mediaservice.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * This class is typically used for updating a Media entry and changing its type
 */
@Schema(
        name = "UpdateMediaTypeRequest",
        description = "Request DTO for updating a media type."
)
@Data
public class UpdateMediaTypeRequest {

    @Schema(description = "The ID of the media type (required)")
    @NotNull
    private Integer id;

    @Schema(description = "The media type name.", example = "Manga")
    private String name;

}
