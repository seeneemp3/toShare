package org.personal.request;

import lombok.RequiredArgsConstructor;
import org.personal.Item.ItemRepository;
import org.personal.user.User;
import org.personal.user.UserRepository;
import org.personal.exeption.RequestNotFoundException;
import org.personal.exeption.UserNotFoundException;
import org.personal.request.dto.CreatedRequestDto;
import org.personal.request.dto.RequestDto;
import org.personal.request.dto.RequestMapper;
import org.personal.request.service.PageRequestMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService{
    private final ItemRepository itemRepository;
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final RequestMapper requestMapper;


    @Override
    public RequestDto create(Long userId, CreatedRequestDto createdRequestDto) {
        Request request = requestMapper.fromDto(createdRequestDto);
        User requester = getUser(userId);
        request.setRequester(requester);
        request.setCreated(LocalDateTime.now());
        return requestMapper.toDto(requestRepository.save(request));
    }

    @Override
    public List<RequestDto> findAllById(Long userId) {
        getUser(userId);
        return requestRepository.findAllByRequesterIdOrderByCreatedDesc(userId).stream()
                .map(requestMapper::toDto).toList();
    }

    @Override
    public RequestDto findById(Long requestId, Long userId) {
        getUser(userId);
        Request request= requestRepository.findById(requestId)
                .orElseThrow(() -> new RequestNotFoundException("Request with id " + requestId + " not found"));
       RequestDto requestDto = requestMapper.toDto(request);
       requestDto.setItems(itemRepository.findAllByRequestId(requestId));
        return requestDto;
    }

    @Override
    public List<RequestDto> getAllLimit(Long userId, Integer from, Integer size) {
        PageRequest pageRequest = PageRequestMapper.pageRequestValidaCreate(from, size);
        List<RequestDto> requestDtos = requestRepository.findAll(pageRequest).getContent()
                .stream().map(requestMapper::toDto).map(i -> {
                    RequestDto requestDto = requestMapper
                            .toDto(requestRepository.findRequestByRequesterId(userId));
                    requestDto.setItems(itemRepository.findAllByRequestId(i.getId()));
                    return requestDto;
                }
                ).toList();
        User requesterOwner = getUser(userId);
        if (requestDtos.stream().anyMatch(requestDto -> requestDto.getRequester().equals(requesterOwner))) {
            return Collections.emptyList();
        }
        return requestDtos;
    }

    private User getUser(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(()-> new UserNotFoundException("User id " + userId + " not found"));
    }
}
