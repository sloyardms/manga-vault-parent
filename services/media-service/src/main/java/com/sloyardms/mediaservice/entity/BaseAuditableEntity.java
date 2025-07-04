package com.sloyardms.mediaservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.Instant;

/**
 * BaseAuditableEntity serves as an abstract base class for entities that require audit-related
 * metadata by tracking the creation and update details, including timestamp and responsible user.
 * It is designed to be extended by other entity classes.
 * <p>
 * Fields:
 *  - createdBy: The identifier of the user who created the entity.
 *  - createdAt: The timestamp when the entity was created, stored in a timezone-aware format.
 *  - updatedBy: The identifier of the user who last updated the entity.
 *  - updatedAt: The timestamp when the entity was last updated, stored in a timezone-aware format.
 * <p>
 * Callbacks:
 *  - prePersist: Lifecycle callback method that sets both the creation and update timestamps
 *    to the current time before the entity is persisted.
 *  - preUpdate: Lifecycle callback method that updates the update timestamp to the current time
 *    before the entity is updated.
 */
@MappedSuperclass
public abstract class BaseAuditableEntity {

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE ")
    private Instant createdAt;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE ")
    private Instant updatedAt;

    @PrePersist
    public void prePersist() {
        Instant now = Instant.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = Instant.now();
    }

}
