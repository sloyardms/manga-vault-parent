package com.sloyardms.mediaservice.mapper;

import com.sloyardms.mediaservice.dto.MediaDetailResponse;
import com.sloyardms.mediaservice.dto.MediaSummaryResponse;
import com.sloyardms.mediaservice.dto.UpdateMediaRequest;
import com.sloyardms.mediaservice.models.Media;
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
