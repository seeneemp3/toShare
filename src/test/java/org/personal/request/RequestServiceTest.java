package org.personal.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.personal.Item.Item;
import org.personal.Item.ItemRepository;
import org.personal.Item.ItemServiceImpl;
import org.personal.Item.comment.CommentDto;
import org.personal.Item.dto.ItemDto;
import org.personal.Item.dto.ItemMapperImpl;
import org.personal.User.User;
import org.personal.User.UserRepository;
import org.personal.User.UserServiceImpl;
import org.personal.User.dto.UserDto;
import org.personal.User.dto.UserMapperImpl;
import org.personal.exeption.RequestNotFoundException;
import org.personal.request.dto.CreatedRequestDto;
import org.personal.request.dto.RequestDto;
import org.personal.request.dto.RequestMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class RequestServiceTest {
    @Mock
    private ItemRepository itemRepository;
    @Mock
    private ItemServiceImpl itemService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserServiceImpl userService;
    @Mock
    private UserMapperImpl userMapper;
    @Mock
    private ItemMapperImpl itemMapper;
    @Mock
    private RequestRepository requestRepository;
    @Mock
    private RequestMapper requestMapper;
    @InjectMocks
    private RequestServiceImpl requestService;
    private User user;
    private User user2;
    private UserDto userDto;
    private UserDto userDto2;
    private Item item;
    private ItemDto itemDto;
    private Request request;
    private CommentDto commentDto;
    private RequestDto requestDto;
    private CreatedRequestDto createdRequestDto;

    @BeforeEach
    public void setup() {
        user = User.builder().id(1L).name("user").email("user@user.com").build();
        user2 = User.builder().id(2L).name("user2").email("user2@user.com").build();
        userDto2 = new UserDto(2L, "user2", "user2@user.com");
        request = new Request(1L, "user2", user, LocalDateTime.now());
        item = new Item(1L, user, "item1", "item1", true, request.getId());
        commentDto = new CommentDto(1L, "commentText", item, user.getName(), LocalDateTime.now());
        itemDto = new ItemDto(1L, user, "item1", "item1", true, List.of(commentDto), request.getId());
        userDto = new UserDto(1L, "user", "user@user.com");
        requestDto = new RequestDto(1L, "req1", user, LocalDateTime.now(), List.of(item));
        request = new Request(1L, "req1", user, LocalDateTime.now());
        createdRequestDto = new CreatedRequestDto("req1");

        when(itemMapper.fromDto(any())).thenReturn(item);
        when(userService.getUserById(any())).thenReturn(userDto2);
        when(userMapper.fromDto(any())).thenReturn(user2);
        when(requestMapper.toDto(any())).thenReturn(requestDto);
        when(requestMapper.fromDto(any())).thenReturn(request);
        when(itemService.getById(1L)).thenReturn(itemDto);
    }

    @Test
    public void testCreate() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(requestMapper.fromDto(createdRequestDto)).thenReturn(request);
        when(requestRepository.save(request)).thenReturn(request);

        RequestDto result = requestService.create(user.getId(), createdRequestDto);
        assertEquals(requestDto, result);
    }

    @Test
    public void testFindAllById() {
        List<Request> requests = Collections.emptyList();
        List<RequestDto> requestDtos = Collections.emptyList();

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(requestRepository.findAllByRequesterIdOrderByCreatedDesc(user.getId())).thenReturn(requests);
        List<RequestDto> result = requestService.findAllById(user.getId());

        assertEquals(requestDtos, result);
    }

    @Test
    public void testFindById() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(requestRepository.findById(request.getId())).thenReturn(Optional.of(request));

        RequestDto result = requestService.findById(request.getId(), user.getId());

        assertEquals(requestDto, result);
        assertThrows(RequestNotFoundException.class, () -> requestRepository.findById(2L));
    }

    @Test
    public void testGetAllLimit() {
        int from = 0;
        int size = 10;
        PageRequest pageRequest = PageRequest.of(from, size);
        List<Request> requests = Collections.emptyList();
        List<RequestDto> requestDtos = Collections.emptyList();
        Page<Request> page = mock(Page.class);

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(requestRepository.findAll(pageRequest)).thenReturn(page);
        when(page.getContent()).thenReturn(requests);

        List<RequestDto> result = requestService.getAllLimit(user.getId(), from, size);

        assertEquals(requestDtos, result);
    }

}
