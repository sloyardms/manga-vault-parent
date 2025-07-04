package com.sloyardms.mediaservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Represents an alternate title for a media entity, allowing for additional or localized names
 * of the media to be stored. This entity is associated with a {@code Media} entity, establishing
 * a many-to-one relationship that enables each media entity to have multiple alternate titles.
 * <p>
 * Fields:
 * - id: The unique identifier for the alternate title.
 * - title: The text of the alternate title itself.
 * - Media: The media entity to which this alternate title belongs, forming a many-to-one relationship.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "media_alternate_titles")
public class MediaAlternateTitle extends BaseAuditableEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "media_id")
    private Media media;

}
