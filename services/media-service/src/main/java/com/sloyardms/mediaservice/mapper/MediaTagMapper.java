package com.sloyardms.mediaservice.mapper;

import com.sloyardms.mediaservice.dto.response.MediaTagResponse;
import com.sloyardms.mediaservice.entity.MediaTag;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MediaTagMapper {

    MediaTagResponse toResponse(MediaTag mediaTag);

    MediaTag toEntity(MediaTagResponse mediaTagResponse);

}
