package com.sloyardms.mediaservice.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Schema(
        name = "CreateMediaAuthorRequest",
        description = "Request DTO for creating a media author."
)
@Data
public class CreateMediaAuthorRequest {

    @Schema(description = "The name of the author.", example = "Masashi Kishimoto")
    @NotBlank
    private String name;

}
