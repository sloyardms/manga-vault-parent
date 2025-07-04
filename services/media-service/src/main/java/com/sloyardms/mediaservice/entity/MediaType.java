package com.sloyardms.mediaservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * This entity is used to classify media into various predefined types, Mangas, Webtoons, Comics
 * or other categories. A unique name identifies each media type.
 * <p>
 * The class extends {@code BaseAuditableEntity}, inheriting audit-related fields and
 * behaviors for tracking creation and modification timestamps.
 * <p>
 * Relationships:
 * - This entity is associated with the {@code Media} entity through a many-to-one relationship to
 *   link media records with their corresponding media type
 * <p>
 * Primary Fields:
 * - {@code id}: The unique identifier for the media type, automatically generated.
 * - {@code name}: The unique name representing a specific type or category of media.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "media_types")
public class MediaType extends BaseAuditableEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", unique = true)
    private String name;

}
