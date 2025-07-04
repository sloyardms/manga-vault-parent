package com.sloyardms.mediaservice.dto.response;

import lombok.Data;

import java.util.UUID;

/**
 * A lightweight response DTO representing a summary of a Media entity.
 * <p>
 * Typically used in listing endpoints to reduce payload size.
 * Contains only basic identifying and descriptive information (e.g., main title, id, thumbnail url)
 */
@Data
public class MediaSummaryResponse {

    private UUID id;
    private String mainTitle;
    private String summary;
    private String thumbnailUrl;

}
