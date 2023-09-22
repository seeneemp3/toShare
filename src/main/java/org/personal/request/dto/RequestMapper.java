package org.personal.request.dto;

import org.mapstruct.Mapper;
import org.personal.request.Request;
import org.springframework.stereotype.Component;


@Mapper(componentModel = "spring")
public interface RequestMapper {
    Request fromDto(CreatedRequestDto createdRequestDto);
    RequestDto toDto(Request request);
}
