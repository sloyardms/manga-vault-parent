package com.sloyardms.mediaparser.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MediaMetadataDto {

    private int version = 1;

    @NotNull
    private UUID id;

    @NotBlank
    private String mainTitle;

    private String summary;
    private Integer releaseYear;

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
