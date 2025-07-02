package com.sloyardms.mediaservice.kafka.events;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

/**
 * Represents the payload of a Kafka message used to request the creation of a new media item (e.g., a Webtoon)
 *
 * <p>This class is consumed by a Kafka listener and contains all necessary fields for creating a media entity,
 * such as titles, authors, tags, and metadata like language and type of media</p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateMediaCommand {

    private int version = 1;

    @NotNull
    private UUID id;

    @NotBlank
    private String mainTitle;

    private String summary;
    private Integer releaseYear;

    @NotBlank
    private String thumbnailUrl;
    private String sourceUrl;

    @NotBlank
    private String mediaStatusName;

    @NotBlank
    private String mediaTypeName;

    private String mediaParodyName;

    @NotBlank
    private String mediaLanguageName;

    private Set<@NotBlank String> mediaAlternateTitles;

    @NotEmpty
    private Set<@NotBlank String> mediaAuthors;
    private Set<@NotBlank String> mediaArtists;

    @NotEmpty
    private Set<@NotBlank String> mediaTags;
    private Set<@NotBlank String> mediaCharacters;

}
