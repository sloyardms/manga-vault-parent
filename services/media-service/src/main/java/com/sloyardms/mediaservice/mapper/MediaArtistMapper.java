package com.sloyardms.mediaservice.mapper;

import com.sloyardms.mediaservice.dto.MediaArtistResponse;
import com.sloyardms.mediaservice.models.MediaArtist;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MediaArtistMapper {

    MediaArtistResponse toResponse(MediaArtist mediaArtist);

    MediaArtist toEntity(MediaArtistResponse mediaArtistResponse);

}
