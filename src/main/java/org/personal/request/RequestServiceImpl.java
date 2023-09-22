package org.personal.request;

import lombok.RequiredArgsConstructor;
import org.personal.Item.ItemRepository;
import org.personal.User.User;
import org.personal.User.UserRepository;
import org.personal.exeption.UserNotFoundException;
import org.personal.request.dto.CreatedRequestDto;
import org.personal.request.dto.RequestDto;
import org.personal.request.dto.RequestMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
        User requester = userRepository.findById(userId)
                .orElseThrow(()-> new UserNotFoundException("User id " + userId + " not found"));
        request.setRequester(requester);
        request.setCreated(LocalDateTime.now());
        return requestMapper.toDto(requestRepository.save(request));
    }

    @Override
    public List<RequestDto> findAllById(Long userId) {
        return null;
    }

    @Override
    public RequestDto findById(Long requestId, Long userId) {
        return null;
    }

    @Override
    public List<RequestDto> getAllLimit(Long userId, Integer from, Integer size) {
        return null;
    }
}
