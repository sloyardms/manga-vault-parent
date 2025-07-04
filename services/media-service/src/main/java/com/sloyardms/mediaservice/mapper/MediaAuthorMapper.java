package com.sloyardms.mediaservice.mapper;

import com.sloyardms.mediaservice.dto.response.MediaAuthorResponse;
import com.sloyardms.mediaservice.entity.MediaAuthor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MediaAuthorMapper {

    MediaAuthorResponse toResponse(MediaAuthor mediaAuthor);

    MediaAuthor toEntity(MediaAuthorResponse mediaAuthorResponse);

}
