package com.sloyardms.mediaservice.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Set;

/**
 * Represents the payload for partially updating a Media entity
 * <p>
 * This DTO is used in the PATCH endpoint to update one or more fields of an existing media.
 * Fields set to {@code} null will be ignored and not updated.
 * <p>
 *     AAudit fields like {@code createdAt}, {@code createdBy} are intentionally excluded.
 */
@Schema(
        name = "UpdateMediaRequest",
        description = "Request DTO for updating a media fields. Fields set to null will be ignored and not updated"
)
@Data
public class UpdateMediaRequest {

    @Schema(description = "The main title of the media", example = "Naruto")
    private String mainTitle;

    @Schema(description = "A small description of the media", example = "Naruto Uzumaki is a young ninja with a sealed demon fox inside him.")
    private String summary;

    @Schema(description = "The year the media was released", example = "1999")
    private Integer releaseYear;

    @Schema(description = "The URL of the media source", example = "https://naruto.com")
    private String sourceUrl;

    @Schema(description = "The status of the media (e.g., Ongoing, Completed)", implementation = UpdateMediaStatusRequest.class)
    private UpdateMediaStatusRequest status;

    @Schema(description = "The type of the media (e.g., Manga, Webtoon)", implementation = UpdateMediaTypeRequest.class)
    private UpdateMediaTypeRequest type;

    @Schema(description = "Information about the parody aspect of the media." +
            " Used when the media makes fun of or takes inspiration from another work, such as another manga.", implementation = UpdateMediaParodyRequest.class)
    private UpdateMediaParodyRequest mediaParody;

    @Schema(description = "Language information of the media (e.g, English, Spanish)", implementation = UpdateMediaLanguageRequest.class)
    private UpdateMediaLanguageRequest mediaLanguage;

    @Schema(description = "Set of alternate titles for the media")
    private Set<UpdateMediaAlternateTitleRequest> mediaAlternateTitles;

    @Schema(description = "Set of authors related to the media")
    private Set<UpdateMediaAuthorRequest> mediaAuthors;

    @Schema(description = "Set of artists related to the media")
    private Set<UpdateMediaArtistRequest> mediaArtists;

    @Schema(description = "Set of tags describing the media content or genre")
    private Set<UpdateMediaTagRequest> mediaTags;

    @Schema(description = "Set of characters appearing in the media")
    private Set<UpdateMediaCharacterRequest> mediaCharacters;

}
