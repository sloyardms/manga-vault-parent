package com.sloyardms.mediaservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Represents a media entity with attributes related to its metadata such as title, summary, release year,
 * and associated entities including status, type, parody, language, and other relationships.
 * The class also maintains links to alternate titles, authors, artists, tags, and characters related to the media.
 * <p>
 * This entity extends {@code BaseAuditableEntity}, inheriting audit-related fields and behavior such as
 * tracking creation and update timestamps.
 */
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

    @Column(name = "main_title")
    private String mainTitle;

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

    @OneToMany(mappedBy = "media", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MediaAlternateTitle> mediaAlternateTitles = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "media_authors", joinColumns = @JoinColumn(name = "media_id"), inverseJoinColumns = @JoinColumn(name = "author_id"))
    private Set<MediaAuthor> mediaAuthors = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "media_artists", joinColumns = @JoinColumn(name = "media_id"), inverseJoinColumns = @JoinColumn(name = "artist_id"))
    private Set<MediaArtist> mediaArtists = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "media_tags", joinColumns = @JoinColumn(name = "media_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<MediaTag> mediaTags = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "media_characters", joinColumns = @JoinColumn(name = "media_id"), inverseJoinColumns = @JoinColumn(name = "character_id"))
    private Set<MediaCharacter> mediaCharacters = new HashSet<>();

}
