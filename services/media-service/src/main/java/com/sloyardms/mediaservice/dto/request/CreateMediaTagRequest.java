package com.sloyardms.mediaservice.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(
        name = "CreateMediaTagRequest",
        description = "Request DTO for creating a media tag.")
@Data
public class CreateMediaTagRequest {

    @Schema(description = "The name of the tag.", example = "Action")
    private String name;

}
