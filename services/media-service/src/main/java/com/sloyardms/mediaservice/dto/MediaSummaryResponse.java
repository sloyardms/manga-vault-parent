package com.sloyardms.mediaservice.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class MediaSummaryResponse {

    private UUID id;
    private String mainTitle;
    private String summary;
    private String thumbnailUrl;

}
