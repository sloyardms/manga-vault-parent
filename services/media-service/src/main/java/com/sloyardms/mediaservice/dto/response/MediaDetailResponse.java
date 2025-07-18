package com.sloyardms.mediaservice.dto.response;

import lombok.Data;

import java.util.Set;
import java.util.UUID;

/**
 * Full detail response DTO for a Media entity
 * <p>
 * Includes all relevant business data but omits internal audit fields
 */
@Data
public class MediaDetailResponse {

    private UUID id;
    private String mainTitle;
    private String summary;
    private Integer releaseYear;
    private String thumbnailUrl;
    private String sourceUrl;
    private MediaStatusResponse status;
    private MediaTypeResponse type;
    private MediaParodyResponse mediaParody;
    private MediaLanguageResponse mediaLanguage;
    private Set<MediaAlternateTitleResponse> mediaAlternateTitles;
    private Set<MediaAuthorResponse> mediaAuthors;
    private Set<MediaArtistResponse> mediaArtists;
    private Set<MediaTagResponse> mediaTags;
    private Set<MediaCharacterResponse> mediaCharacters;

}
