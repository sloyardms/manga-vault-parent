package com.sloyardms.mediaservice.mapper;

import com.sloyardms.mediaservice.dto.MediaAlternateTitleResponse;
import com.sloyardms.mediaservice.models.MediaAlternateTitle;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MediaAlternateTitleMapper {

    MediaAlternateTitleResponse toResponse(MediaAlternateTitle mediaAlternateTitle);

    MediaAlternateTitle toEntity(MediaAlternateTitleResponse mediaAlternateTitleResponse);

}
