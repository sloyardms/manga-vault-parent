package com.sloyardms.mediaservice.mapper;

import com.sloyardms.mediaservice.dto.response.MediaDetailResponse;
import com.sloyardms.mediaservice.dto.response.MediaSummaryResponse;
import com.sloyardms.mediaservice.dto.request.UpdateMediaRequest;
import com.sloyardms.mediaservice.entity.Media;
import com.sloyardms.mediaservice.projection.MediaSummaryProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {
        MediaAlternateTitleMapper.class,
        MediaArtistMapper.class,
        MediaAuthorMapper.class,
        MediaCharacterMapper.class,
        MediaLanguageMapper.class,
        MediaParodyMapper.class,
        MediaStatusMapper.class,
        MediaTagMapper.class,
        MediaTypeMapper.class
})
public interface MediaMapper {

    MediaSummaryResponse toSummaryResponse(Media media);

    MediaSummaryResponse toSummaryResponse(MediaSummaryProjection mediaSummaryProjection);

    MediaDetailResponse toDetailResponse(Media media);

    Media toEntity(UpdateMediaRequest updateMediaRequest);

}
