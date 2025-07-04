package com.sloyardms.mediaservice.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * This class is typically used for updating a Media entry and changing its status
 */
@Schema(
        name = "UpdateMediaStatusRequest",
        description = "Request DTO for updating a media status."
)
@Data
public class UpdateMediaStatusRequest {

    @Schema(description = "The ID of the media status (required)")
    @NotNull
    private Integer id;

    @Schema(description = "The media status name.", example = "Ongoing")
    private String name;

}
