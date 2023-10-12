package org.personal.item.service;

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
import org.personal.Item.comment.Comment;
import org.personal.Item.comment.CommentDto;
import org.personal.Item.comment.CommentMapper;
import org.personal.Item.comment.CommentRepository;
import org.personal.Item.dto.ItemDto;
import org.personal.Item.dto.ItemMapperImpl;
import org.personal.user.User;
import org.personal.user.UserRepository;
import org.personal.user.UserServiceImpl;
import org.personal.user.dto.UserMapperImpl;
import org.personal.booking.Booking;
import org.personal.booking.BookingRepository;
import org.personal.booking.BookingStatus;
import org.personal.exeption.BookingDataException;
import org.personal.exeption.InvalidInputDataException;
import org.personal.exeption.ItemNotFoundException;
import org.personal.exeption.UserNotFoundException;
import org.personal.request.Request;
import org.personal.request.RequestRepository;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
public class ItemServiceImplTest {
    @InjectMocks
    private ItemServiceImpl service;
    @Mock
    private ItemRepository itemRepository;
    @Mock
    private ItemMapperImpl itemMapper;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserServiceImpl userService;
    @Mock
    private UserMapperImpl userMapper;
    @Mock
    private BookingRepository bookingRepository;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private CommentMapper commentMapper;
    @Mock
    private RequestRepository requestRepository;

    private User user;
    private Booking booking;
    private Item item;
    private CommentDto commentDto;
    private Comment comment;
    private Request request;
    private ItemDto itemDto;

    @BeforeEach
    void setup() {

        user = User.builder()
                .id(1L)
                .name("user")
                .email("user@user.com")
                .build();
        request = new Request(1L, "user2", user, LocalDateTime.now());
        item = new Item(1L, user, "item1", "item1", true, request.getId());

        booking = new Booking(1L, item,
                LocalDateTime.of(2023, Month.FEBRUARY, 1, 12, 0),
                LocalDateTime.of(2023, Month.FEBRUARY, 2, 12, 0),
                user, BookingStatus.APPROVED);

        commentDto = new CommentDto(1L, "commentText", item, user.getName(), LocalDateTime.now());
        itemDto = new ItemDto(1L, user, "item1", "item1", true, List.of(commentDto), request.getId());
        comment = new Comment(1L, "commentText", LocalDateTime.now(), item, user);

        when(itemMapper.toDto(any(Item.class))).thenReturn(itemDto);
        when(itemMapper.fromDto(any(ItemDto.class))).thenReturn(item);

        when(commentMapper.dtoToComment(any(CommentDto.class))).thenReturn(comment);
        when(commentMapper.commentToDto(any(Comment.class))).thenReturn(commentDto);
    }

    @Test
    public void getAll() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(itemRepository.findByOwnerId(user.getId())).thenReturn(List.of(item));

        itemDto.setComments(List.of(commentDto));
        List<ItemDto> items = service.getAll(user.getId());

        assertNotNull(items);
        assertEquals(items.get(0).getId(), item.getId());

        assertThrows(UserNotFoundException.class, () -> service.getAll(3L));
    }

    @Test
    public void getById() {
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        assertEquals(service.getById(1L), itemMapper.toDto(item));

        assertThrows(ItemNotFoundException.class, () -> service.getById(3L));
    }

    @Test
    public void add() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        assertThrows(UserNotFoundException.class, () -> service.add(999L, itemDto));
        when(requestRepository.findById(1L)).thenReturn(Optional.of(request));
        when(itemRepository.save(any(Item.class))).thenReturn(item);

        itemDto.setRequestId(1L);
        ItemDto addedItem = service.add(user.getId(), itemDto);
        assertNotNull(addedItem);

        itemDto.setRequestId(null);
        addedItem = service.add(user.getId(), itemDto);
        assertNull(addedItem.getRequestId());

        assertThrows(InvalidInputDataException.class, () -> {
            itemDto.setName("");
            service.add(user.getId(), itemDto);
        });
    }

    @Test
    public void update() {
        ItemDto itemDto = itemMapper.toDto(item);
        user.setId(user.getId() + 1);

        ItemDto updatedItemDto = ItemDto.builder()
                .id(1L)
                .name("itemUpdated")
                .description("itemUpdated")
                .owner(user)
                .requestId(request.getId() + 1)
                .available(false)
                .build();

        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        when(requestRepository.findById(anyLong())).thenReturn(Optional.of(request));
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        assertEquals(request.getId(), service.update(2L, 1L, updatedItemDto).getRequestId());

        when(itemRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ItemNotFoundException.class, () ->
                service.update(1L, 1L, itemDto));

        when(itemRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ItemNotFoundException.class, () -> {
            itemDto.setOwner(user);
            service.update(1L, 1L, itemDto);
        });

        when(itemRepository.findById(1L)).thenReturn(Optional.of(Item.builder()
                .id(1L)
                .name("item1")
                .description("item1")
                .owner(User.builder().id(1000L).build())
                .requestId(request.getId())
                .available(true)
                .build()));

        assertThrows(UserNotFoundException.class, () -> {
            service.update(1L, 1L, itemDto);
        });

        when(requestRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> {
            service.update(1L, 1L, updatedItemDto);
        });
    }

    @Test
    public void delete() {
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        assertDoesNotThrow(() -> service.delete(1L, 1L));
        assertThrows(ItemNotFoundException.class, () -> service.delete(1L, 999L));
    }

    @Test
    public void search() {
        when(itemRepository.getItemsByKeywordNative("item1")).thenReturn(List.of(item));
        List<ItemDto> items = service.search("item1");
        assertNotNull(items);
        assertEquals(1, items.size());
        assertEquals("item1", items.get(0).getName());

        items = service.search("");
        assertTrue(items.isEmpty());
    }

    @Test
    public void createComment() {
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        when(commentRepository.save(any(Comment.class))).thenReturn(new Comment());
        assertThrows(BookingDataException.class, () -> service.createComment(1L, 1L, commentDto));
        assertThrows(ItemNotFoundException.class, () -> service.createComment(1L, 999L, commentDto));
        when(bookingRepository.findFirstByItem_IdAndBooker_IdAndEndIsBefore(anyLong(), anyLong(), any(LocalDateTime.class)))
                .thenReturn(booking);

        assertDoesNotThrow(() -> service.createComment(1L, 1L, commentDto));
    }

    @Test
    public void getCommentsByItemId() {
        when(commentRepository.findAllByItem_Id(anyLong(), any(Sort.class))).thenReturn(List.of(comment));
        List<CommentDto> comments = service.getCommentsByItemId(1L);
        assertNotNull(comments);
        assertEquals(1, comments.size());
    }
}






