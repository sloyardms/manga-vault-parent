package com.sloyardms.mediaservice.mapper;

import com.sloyardms.mediaservice.dto.response.MediaLanguageResponse;
import com.sloyardms.mediaservice.entity.MediaLanguage;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MediaLanguageMapper {

    MediaLanguageResponse toResponse(MediaLanguage mediaLanguage);

    MediaLanguage toEntity(MediaLanguageResponse mediaLanguageResponse);

}
