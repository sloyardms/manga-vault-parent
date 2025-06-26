package com.sloyardms.mediaservice.service.interfaces;

import com.sloyardms.mediaservice.models.Media;

import java.util.Optional;
import java.util.UUID;

public interface IMediaService {

    public Media create(Media media);

    public Optional<Media> getById(UUID id);

    public Media update(UUID id, Media media);

    public void delete(UUID id);

}
