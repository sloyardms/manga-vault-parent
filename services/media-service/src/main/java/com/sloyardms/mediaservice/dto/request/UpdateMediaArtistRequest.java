package com.sloyardms.mediaservice.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(
        name = "UpdateMediaArtistRequest",
        description = "Request DTO for updating a media artist."
)
@Data
public class UpdateMediaArtistRequest {

    @Schema(description = "The ID of the artist. If null, a new entry will be created using the name field", example = "1")
    private Integer id;

    @Schema(description = "The name of the artist.", example = "Masashi Kishimoto")
    private String name;

}
