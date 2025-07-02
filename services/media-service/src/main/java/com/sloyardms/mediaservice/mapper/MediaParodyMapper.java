package com.sloyardms.mediaservice.mapper;

import com.sloyardms.mediaservice.dto.MediaParodyResponse;
import com.sloyardms.mediaservice.models.MediaParody;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MediaParodyMapper {

    MediaParodyResponse toResponse(MediaParody mediaParody);

    MediaParody toEntity(MediaParodyResponse mediaParodyResponse);

}
