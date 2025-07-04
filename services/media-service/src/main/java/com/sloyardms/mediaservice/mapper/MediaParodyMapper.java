package com.sloyardms.mediaservice.mapper;

import com.sloyardms.mediaservice.dto.response.MediaParodyResponse;
import com.sloyardms.mediaservice.entity.MediaParody;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MediaParodyMapper {

    MediaParodyResponse toResponse(MediaParody mediaParody);

    MediaParody toEntity(MediaParodyResponse mediaParodyResponse);

}
