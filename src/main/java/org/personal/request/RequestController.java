package org.personal.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.personal.request.dto.CreatedRequestDto;
import org.personal.request.dto.RequestDto;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/requests")
@RequiredArgsConstructor
public class RequestController {
    private static final String USER_ID = "X-Sharer-User-Id";
    private final RequestService requestService;

    @PostMapping
    public RequestDto add(@RequestHeader(USER_ID) Long userId, @Valid @RequestBody CreatedRequestDto createdRequestDto) {
        return requestService.create(userId, createdRequestDto);
    }
    @GetMapping
    public List<RequestDto> findAllById(@RequestHeader(USER_ID) Long userId){
        return requestService.findAllById(userId);
    }
    @GetMapping("/{requestId}")
    RequestDto findById(@PathVariable Long requestId, @RequestHeader(USER_ID) Long userId){
       return requestService.findById(requestId, userId);
    }
    @GetMapping("/all")
    List<RequestDto> getAllLimit(@RequestHeader(USER_ID) Long userId,
                                 @RequestParam(required = false, defaultValue = "0") Integer from,
                                 @RequestParam(required = false, defaultValue = "1") Integer size){
       return requestService.getAllLimit(userId,from,size);
    }
}
