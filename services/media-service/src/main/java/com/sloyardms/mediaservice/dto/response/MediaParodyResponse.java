package com.sloyardms.mediaservice.dto.response;

import lombok.Data;

/**
 * Response DTO representing a parody associated with a media entry.
 * <p>
 * Includes only essential identifying fields such as {@code id} and {@code name}.
 * Typically used as part of a {@link MediaDetailResponse}.
 */
@Data
public class MediaParodyResponse {

    private Integer id;
    private String name;

}
