package com.sloyardms.mediaservice.dto.response;

import lombok.Data;

/**
 * Response DTO representing an alternate title associated with a media entry.
 * <p>
 * Includes only essential identifying fields such as {@code id} and {@code title}.
 * Typically used as part of a {@link MediaDetailResponse}.
 */
@Data
public class MediaAlternateTitleResponse {

    private Integer id;
    private String title;

}
