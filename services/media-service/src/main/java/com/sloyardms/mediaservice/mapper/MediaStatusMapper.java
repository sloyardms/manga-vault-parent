package com.sloyardms.mediaservice.mapper;

import com.sloyardms.mediaservice.dto.response.MediaStatusResponse;
import com.sloyardms.mediaservice.entity.MediaStatus;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MediaStatusMapper {

    MediaStatusResponse toResponse(MediaStatus mediaStatus);

    MediaStatus toEntity(MediaStatusResponse mediaStatusResponse);

}
