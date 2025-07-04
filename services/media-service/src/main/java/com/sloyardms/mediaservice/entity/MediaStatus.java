package com.sloyardms.mediaservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Represents the status of a media entity. Media statuses can be used to categorize
 * or define the current state of a media resource (e.g., Completed, Publishing, Axed).
 * <p>
 * The class extends {@code BaseAuditableEntity}, inheriting audit-related fields and
 * behaviors for tracking creation and modification timestamps.
 * <p>
 * Fields:
 * - {@code id}: The unique identifier for the media status, automatically generated.
 * - {@code name}: The name of the media status, which must be unique.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "media_status")
public class MediaStatus extends BaseAuditableEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", unique = true)
    private String name;

}
