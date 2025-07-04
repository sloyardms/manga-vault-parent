package com.sloyardms.mediaservice.kafka.topics;

public class KafkaMediaTopics {

    private KafkaMediaTopics() {}

    // Commands for creating media
    public static final String MEDIA_CREATE_COMMAND = "media-service.commands.create";
    public static final String MEDIA_CREATE_COMMAND_DLT = "media-service.commands.create.dlt";

    // Events for success/error feedback
    public static final String MEDIA_CREATED_EVENT = "media-service.events.media-created";
    public static final String MEDIA_CREATION_FAILED_EVENT = "media-service.events.media-creation-failed";

}
