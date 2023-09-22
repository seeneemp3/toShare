package org.personal.request;

import org.personal.request.dto.CreatedRequestDto;
import org.personal.request.dto.RequestDto;

import java.util.List;

public interface RequestService {
    RequestDto create(Long userId, CreatedRequestDto createdRequestDto);
    List<RequestDto> findAllById(Long userId);
    RequestDto findById(Long requestId, Long userId);
    List<RequestDto> getAllLimit(Long userId, Integer from, Integer size);
}
