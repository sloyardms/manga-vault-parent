package com.sloyardms.mediaservice.mapper;

import com.sloyardms.mediaservice.dto.MediaTypeResponse;
import com.sloyardms.mediaservice.models.MediaType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MediaTypeMapper {

    MediaTypeResponse toResponse(MediaType mediaType);

    MediaType toEntity(MediaTypeResponse mediaTypeResponse);

}
