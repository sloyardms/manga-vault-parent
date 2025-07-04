package com.sloyardms.mediaservice.mapper;

import com.sloyardms.mediaservice.dto.response.MediaArtistResponse;
import com.sloyardms.mediaservice.entity.MediaArtist;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MediaArtistMapper {

    MediaArtistResponse toResponse(MediaArtist mediaArtist);

    MediaArtist toEntity(MediaArtistResponse mediaArtistResponse);

}
