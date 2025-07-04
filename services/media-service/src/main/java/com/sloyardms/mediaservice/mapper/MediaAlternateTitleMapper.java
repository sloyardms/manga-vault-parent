package com.sloyardms.mediaservice.mapper;

import com.sloyardms.mediaservice.dto.response.MediaAlternateTitleResponse;
import com.sloyardms.mediaservice.entity.MediaAlternateTitle;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MediaAlternateTitleMapper {

    MediaAlternateTitleResponse toResponse(MediaAlternateTitle mediaAlternateTitle);

    MediaAlternateTitle toEntity(MediaAlternateTitleResponse mediaAlternateTitleResponse);

}
