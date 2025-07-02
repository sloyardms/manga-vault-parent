package com.sloyardms.mediaservice.mapper;

import com.sloyardms.mediaservice.dto.MediaTagResponse;
import com.sloyardms.mediaservice.models.MediaTag;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MediaTagMapper {

    MediaTagResponse toResponse(MediaTag mediaTag);

    MediaTag toEntity(MediaTagResponse mediaTagResponse);

}
