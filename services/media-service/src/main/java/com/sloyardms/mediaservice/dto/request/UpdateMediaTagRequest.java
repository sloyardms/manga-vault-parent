package com.sloyardms.mediaservice.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


@Schema(
        name = "UpdateMediaTagRequest",
        description = "Request DTO for updating a media tag."
)
@Data
public class UpdateMediaTagRequest {

    @Schema(description = "The ID of the tag. If null, a new entry will be created using the name field", example = "1")
    private Integer id;

    @Schema(description = "The new name.", example = "Action")
    private String name;

}
