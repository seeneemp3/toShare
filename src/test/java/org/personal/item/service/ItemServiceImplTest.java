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
import org.personal.Item.comment.CommentRepository;
import org.personal.Item.dto.ItemDto;
import org.personal.Item.dto.ItemMapperImpl;
import org.personal.User.User;
import org.personal.User.UserRepository;
import org.personal.booking.Booking;
import org.personal.booking.BookingRepository;
import org.personal.booking.BookingStatus;
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
import static org.mockito.ArgumentMatchers.*;
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
    private BookingRepository bookingRepository;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private RequestRepository requestRepository;

    private User user;
    private Booking booking;
    private Item item;
    private CommentDto commentDto;
    private Request request;
    private ItemDto itemDto;
    @BeforeEach
    void setup(){
        user = User.builder()
                .id(1L)
                .name("user")
                .email("user@user.com")
                .build();

        booking = new Booking(1L, item,
                LocalDateTime.of(2023, Month.FEBRUARY, 1, 12, 0),
                LocalDateTime.of(2023, Month.FEBRUARY, 2, 12, 0),
                user, BookingStatus.WAITING);

        request = new Request(1L,"user2", user, LocalDateTime.now());
        item = new Item(1L,user,"item1","item1",true, request.getId());
        itemDto = new ItemDto(1L,user,"item1","item1",true, null,request.getId());
        commentDto = new CommentDto(1L, "commentText", item, user.getName(), LocalDateTime.now());

        when(itemMapper.toDto(any(Item.class))).thenReturn(itemDto);
        when(itemMapper.fromDto(any(ItemDto.class))).thenReturn(item);
    }
    @Test
    public void getAll(){
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(itemRepository.findByOwnerId(user.getId())).thenReturn(List.of(item));

        itemDto.setComments(List.of(commentDto));
        List<ItemDto> items = service.getAll(user.getId());

        assertNotNull(items);
        assertEquals(items.get(0).getId(), item.getId());

        assertThrows(UserNotFoundException.class, () -> service.getAll(3L));
    }
    @Test
    public void getById(){
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        assertEquals(service.getById(1L), itemMapper.toDto(item));

        assertThrows(ItemNotFoundException.class, () -> service.getById(3L));
    }
    @Test
    public void add(){
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
    public void update(){
        ItemDto itemDto = itemMapper.toDto(item);
        user.setId(user.getId()+1);

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
        System.out.println(service.update(2L, 1L, updatedItemDto));
        assertEquals(request.getId(), service.update(2L, 1L, updatedItemDto).getRequestId());

        when(itemRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            service.update(1L, 1L, itemDto);
        });

        when(itemRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ItemNotFoundException.class, () -> {
            itemDto.setOwner(user);
            service.update(1L, 1L, itemDto);
        });

//        when(itemRepository.findById(1L)).thenReturn(Optional.of(Item.builder()
//                .id(1L)
//                .name("item1")
//                .description("item1")
//                .owner(User.builder().id(1000L).build())
//                .request(itemRequest)
//                .available(true)
//                .build()));
//
//        assertThrows(ObjectNotFoundException.class, () -> {
//            itemService.updateItem(1L, 1L, itemDto);
//        });
//
//        when(itemRequestRepository.findById(anyLong())).thenReturn(Optional.empty());
//        assertThrows(ObjectNotFoundException.class, () -> {
//            itemService.updateItem(1L, 1L, updatedItemDto);
//        });
    }
    @Test
    public void delete(){

    }
    @Test
    public void search(){

    }
    @Test
    public void createComment(){

    }
    @Test
    public void getCommentsByItemId(){

    }
}






