package com.sloyardms.mediaservice.kafka.events;

import lombok.Data;

import java.time.Instant;
import java.util.UUID;

/**
 * Kafka event emitted by media-service when Media creation fails
 */
@Data
public class MediaCreationFailedEvent {

    private UUID id;
    private String reason;
    private String errorType;
    private Instant failedAt;

}
