package com.sloyardms.mediaservice.kafka;

import com.sloyardms.mediaservice.errors.InvalidResourceVersionException;
import com.sloyardms.mediaservice.kafka.events.CreateMediaCommand;
import com.sloyardms.mediaservice.kafka.events.MediaCreatedEvent;
import com.sloyardms.mediaservice.kafka.events.MediaCreationFailedEvent;
import com.sloyardms.mediaservice.kafka.topics.KafkaMediaTopics;
import com.sloyardms.mediaservice.service.interfaces.MediaService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import jakarta.ws.rs.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class KafkaConsumer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    private final KafkaProducer kafkaProducer;
    private final MediaService mediaService;
    private final Validator validator;

    public KafkaConsumer(KafkaProducer kafkaProducer, MediaService mediaService, Validator validator) {
        this.kafkaProducer = kafkaProducer;
        this.mediaService = mediaService;
        this.validator = validator;
    }

    /*
    @RetryableTopic(
            attempts = "3",
            backoff = @Backoff(delay = 3000, multiplier = 2.0),
            dltTopicSuffix = ".dlt"
    )
     */
    @KafkaListener(
            topics = KafkaMediaTopics.MEDIA_CREATE_COMMAND,
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consumeMediaCreationCommand(CreateMediaCommand command) {
        try {
            // Validate the incoming kafka command
            validateCreateMediaCommand(command);

            // Save the data to database
            logger.info("Received command for media with ID: {}", command.getId());
            mediaService.create(command);

            sendSuccessEvent(command.getId());
        } catch (BadRequestException e) {
            String reason = "Validation failed: " + e.getMessage();
            sendFailureEvent(command.getId(), reason, e);

        } catch (DataIntegrityViolationException ex) {
            Throwable rootCause = ex.getCause();
            String reason;

            if (rootCause instanceof ConstraintViolationException) {
                reason = "Constraint violation (Possible duplicate or invalid reference): " + rootCause.getMessage();
            } else {
                reason = "Data integrity violation: " + ex.getMessage();
            }

            sendFailureEvent(command.getId(), reason, ex);

        } catch(InvalidResourceVersionException ex){
            sendFailureEvent(command.getId(), ex.getMessage(), ex);

        }catch (Exception e) {
            String reason = "Unexpected error" + e.getMessage();
            sendFailureEvent(command.getId(), reason, e);
        }
    }

    /**
     * Sends a {@link MediaCreationFailedEvent} to the Kafka topic configured for media creation failures.
     * <p>
     * This method is typically invoked when an error occurs during the processing of a {@link CreateMediaCommand}. It
     * logs the error and sends an event containing the failure reason and timestamp to inform downstream systems of the
     * failure
     *
     * @param id the UUID of the media that failed to be created
     * @param reason a human-readable message describing the reason for the failure
     * @param e the exception that caused the failure (used for logging)
     */
    private void sendFailureEvent(UUID id, String reason, Exception e){
        logger.error(reason, e);
        MediaCreationFailedEvent failedEvent = new MediaCreationFailedEvent();
        failedEvent.setFailedAt(Instant.now());
        failedEvent.setId(id);
        failedEvent.setReason(reason);
        kafkaProducer.sendMessage(KafkaMediaTopics.MEDIA_CREATION_FAILED_EVENT, failedEvent);
    }

    /**
     * Sends a {@link MediaCreatedEvent} to the Kafka topic dedicated for media creation events.
     * <p>
     * This method is typically called after a {@link CreateMediaCommand} entity has been successfully parsed and persisted to the database.
     * It publishes an event containing the media entity's ID and the creation timestamp, allowing downstream systems to react accordingly.
     *
     * @param id the UUID of the media entity that was saved
     */
    private void sendSuccessEvent(UUID id){
        MediaCreatedEvent successEvent = new MediaCreatedEvent();
        successEvent.setId(id);
        successEvent.setCreatedAt(Instant.now());
        kafkaProducer.sendMessage(KafkaMediaTopics.MEDIA_CREATED_EVENT, successEvent);
    }

    /**
     * Validates the given {@link CreateMediaCommand} object using the injected {@link Validator}.
     * <p>
     * If any constraint violations are found, it logs the violations and throws a {@link BadRequestException}
     * containing all violation messages.
     *
     * @param command the {@link CreateMediaCommand} instance to validate
     */
    private void validateCreateMediaCommand(CreateMediaCommand command) {
        //Validate the command object
        Set<ConstraintViolation<CreateMediaCommand>> violations = validator.validate(command);
        if (!violations.isEmpty()) {
            String errorMessage = violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining(", "));
            throw new BadRequestException(errorMessage);
        }
    }

}
