package com.sloyardms.mediaservice.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;
import java.util.UUID;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Entity
@Table(
        name = "media",
        indexes = {
                @Index(name = "idx_media_status", columnList = "media_status_id"),
                @Index(name = "idx_media_type_id", columnList = "media_type_id"),
                @Index(name = "idx_media_language_id", columnList = "media_language_id")
        }
)
public class Media extends BaseAuditableEntity{

    @EqualsAndHashCode.Include
    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "summary")
    private String summary;

    @Column(name = "release_year")
    private Integer releaseYear;

    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    @Column(name = "source_url")
    private String sourceUrl;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "media_status_id")
    private MediaStatus status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "media_type_id")
    private MediaType type;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parody_id")
    private MediaParody mediaParody;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "media_language_id")
    private MediaLanguage mediaLanguage;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<MediaTitle> mediaTitles;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "media_authors", joinColumns = @JoinColumn(name = "media_id"), inverseJoinColumns = @JoinColumn(name = "author_id"))
    private Set<MediaAuthor> mediaAuthors;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "media_artists", joinColumns = @JoinColumn(name = "media_id"), inverseJoinColumns = @JoinColumn(name = "artist_id"))
    private Set<MediaArtist> mediaArtists;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "media_tags", joinColumns = @JoinColumn(name = "media_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<MediaTag> mediaTags;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "media_characters", joinColumns = @JoinColumn(name = "media_id"), inverseJoinColumns = @JoinColumn(name = "character_id"))
    private Set<MediaCharacter> mediaCharacters;

}
