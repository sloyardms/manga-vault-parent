package com.sloyardms.mediaservice.mapper;

import com.sloyardms.mediaservice.dto.MediaStatusResponse;
import com.sloyardms.mediaservice.models.MediaStatus;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MediaStatusMapper {

    MediaStatusResponse toResponse(MediaStatus mediaStatus);

    MediaStatus toEntity(MediaStatusResponse mediaStatusResponse);

}
