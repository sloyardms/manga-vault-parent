package com.sloyardms.mediaservice.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(
        name = "UpdateMediaParodyRequest",
        description = "Request DTO for updating a media parody."
)
@Data
public class UpdateMediaParodyRequest {

    @Schema(description = "The ID of the parody. If null, a new entry will be created using the name field", example = "1")
    private Integer id;

    @Schema(description = "The new name of the parody.", example = "Original")
    private String name;

}
