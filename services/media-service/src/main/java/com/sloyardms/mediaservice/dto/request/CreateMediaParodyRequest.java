package com.sloyardms.mediaservice.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(
        name = "CreateMediaParodyRequest",
        description = "Request DTO for creating a media parody."
)
@Data
public class CreateMediaParodyRequest {

    @Schema(description = "The name of the parody.", example = "Original")
    private String name;

}
