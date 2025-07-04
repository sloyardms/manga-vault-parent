package com.sloyardms.mediaservice.config;

import com.sloyardms.mediaservice.kafka.topics.KafkaMediaTopics;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicsConfig {

    @Bean
    public NewTopic mediaCreationCommandTopic(){
        return new NewTopic(KafkaMediaTopics.MEDIA_CREATE_COMMAND, 1, (short) 1);
    }

    @Bean
    public NewTopic mediaCreateCommandDlt() {
        return new NewTopic(KafkaMediaTopics.MEDIA_CREATE_COMMAND_DLT, 1, (short) 1);
    }

    @Bean
    public NewTopic mediaCreatedEventTopic(){
        return new NewTopic(KafkaMediaTopics.MEDIA_CREATED_EVENT, 1, (short) 1);
    }

    @Bean
    public NewTopic mediaCreationFailedEventTopic(){
        return new NewTopic(KafkaMediaTopics.MEDIA_CREATION_FAILED_EVENT, 1, (short) 1);
    }

}
