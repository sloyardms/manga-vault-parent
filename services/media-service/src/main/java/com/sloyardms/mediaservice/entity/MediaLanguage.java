package com.sloyardms.mediaservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Represents a media language entity in the system, used to define and manage the languages
 * associated with media content. Each instance represents a single language.
 * <p>
 * This class is mapped to the "languages" table in the database, and the field 'name' is unique
 * for each language entry.
 * <p>
 * Primary Fields:
 * - {@code id}: The unique identifier for the language, automatically generated.
 * - {@code name}: The name of the language, which must be unique in the database.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "languages")
public class MediaLanguage extends BaseAuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", unique = true)
    private String name;

}
