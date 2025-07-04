package com.sloyardms.mediaservice.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Schema(
        name = "CreateMediaArtistRequest",
        description = "Request DTO for creating a media artist."
)
@Data
public class CreateMediaArtistRequest {

    @Schema(description = "The name of the artist.", example = "Masashi Kishimoto")
    @NotBlank
    private String name;

}
