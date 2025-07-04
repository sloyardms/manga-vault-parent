package com.sloyardms.mediaservice.mapper;

import com.sloyardms.mediaservice.dto.response.MediaCharacterResponse;
import com.sloyardms.mediaservice.entity.MediaCharacter;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MediaCharacterMapper {

    MediaCharacterResponse toResponse(MediaCharacter mediaCharacter);

    MediaCharacter toEntity(MediaCharacterResponse response);

}
