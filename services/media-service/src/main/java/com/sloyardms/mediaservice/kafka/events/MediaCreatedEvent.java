package com.sloyardms.mediaservice.kafka.events;

import lombok.Data;

import java.time.Instant;
import java.util.UUID;

/**
 * Kafka event emitted by media-service when a Media item is successfully created
 */
@Data
public class MediaCreatedEvent {

    private UUID id;
    private Instant createdAt;

}
