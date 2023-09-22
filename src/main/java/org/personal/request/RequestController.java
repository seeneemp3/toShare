package org.personal.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.personal.request.dto.CreatedRequestDto;
import org.personal.request.dto.RequestDto;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
@Slf4j
@RestController
@RequestMapping("/requests")
@RequiredArgsConstructor
public class RequestController {
    private static final String USER_ID = "X-Sharer-User-Id";
    private final RequestService requestService;

    @PostMapping
    public RequestDto add(@RequestHeader(USER_ID) Long userId, @RequestBody CreatedRequestDto createdRequestDto) {
        return requestService.create(userId, createdRequestDto);
    }
}
