package com.sloyardms.mediaservice.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(
        name = "UpdateMediaAlternateTitleRequest",
        description = "Request DTO for updating a media alternate title."
)
@Data
public class UpdateMediaAlternateTitleRequest {

    @Schema(description = "The ID of the alternate title. If null, a new entry will be created using the title field", example = "1")
    private Integer id;

    @Schema(description = "The new title.", example = "ナルト")
    private String title;

}
