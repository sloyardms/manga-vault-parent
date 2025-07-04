package com.sloyardms.mediaservice.service.interfaces;

import com.sloyardms.mediaservice.dto.request.UpdateMediaRequest;
import com.sloyardms.mediaservice.kafka.events.CreateMediaCommand;
import com.sloyardms.mediaservice.entity.Media;
import com.sloyardms.mediaservice.projection.MediaSummaryProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * CRUD for Media entities
 */
public interface MediaService {

    /**
     * Creates a new Media
     * @param createMediaCommand the dto received from kafka to create
     * @return the created Media
     */
    Media create(CreateMediaCommand createMediaCommand);

    /**
     * Retrieves a Media by its UUID
     * @param id the UUID of the Media
     * @return the Media entity, if found
     */
    Media getById(UUID id);

    /**
     * Retrieves a paginated list of Media entities
     * @param pageable the pagination and sorting information
     * @return a page of Media entities
     */
    Page<MediaSummaryProjection> getAll(Pageable pageable);

    /**
     * Updates an existing Media
     * @param id the UUID of the Media to update
     * @param updateMediaRequest the updated Media data
     * @return the updated Media
     */
    Media update(UUID id, UpdateMediaRequest updateMediaRequest);

    /**
     * Deletes a Media by its UUID
     * @param id the UUID of the Media to delete
     */
    void delete(UUID id);

    /**
     * Updates the thumbnail of an existing media
     * @param id the UUID of the Media to update
     * @param file MultiPartFile with the uploaded file
     * @return the updated Media
     */
    Media uploadThumbnail(UUID id, MultipartFile file);

}
