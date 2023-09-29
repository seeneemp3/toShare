package org.personal.booking.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.personal.Item.Item;
import org.personal.Item.ItemServiceImpl;
import org.personal.Item.comment.CommentDto;
import org.personal.Item.dto.ItemDto;
import org.personal.Item.dto.ItemMapper;
import org.personal.User.User;
import org.personal.User.UserServiceImpl;
import org.personal.User.dto.UserDto;
import org.personal.User.dto.UserMapper;
import org.personal.booking.Booking;
import org.personal.booking.BookingStatus;
import org.personal.booking.dto.BookingDto;
import org.personal.booking.dto.BookingDtoInput;
import org.personal.booking.dto.BookingDtoShort;
import org.personal.booking.dto.BookingMapper;
import org.personal.request.Request;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class BookingMapperTest {

    @InjectMocks
    private BookingMapper bookingMapper;

    @Mock
    private UserServiceImpl userService;

    @Mock
    private ItemServiceImpl itemService;

    @Mock
    private UserMapper userMapper;

    @Mock
    private ItemMapper itemMapper;

    private User user;
    private UserDto userDto;
    private Item item;
    private ItemDto itemDto;
    private Booking booking;
    private Request request;
    private CommentDto commentDto;


    @BeforeEach
    public void setup() {
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
        userDto = new UserDto(1L, "user", "user@user.com");
    }

    @Test
    public void testToDto() {

        when(userMapper.toDto(any())).thenReturn(userDto);
        when(itemMapper.toDto(any())).thenReturn(itemDto);

        BookingDto dto = bookingMapper.toDto(booking);

        assertEquals(booking.getId(), dto.getId());
        assertEquals(booking.getStart(), dto.getStart());
        assertEquals(booking.getEnd(), dto.getEnd());
        assertEquals(booking.getItem(), dto.getItem());
        assertEquals(booking.getBooker(), dto.getBooker());
        assertEquals(booking.getStatus(), dto.getStatus());

    }

    @Test
    public void testFromDto() {
        BookingDtoInput bookingDtoInput = new BookingDtoInput(item.getId(),
                LocalDateTime.of(2023, Month.FEBRUARY, 1, 12, 0),
                LocalDateTime.of(2023, Month.FEBRUARY, 2, 12, 0));

        when(userService.getUserById(anyLong())).thenReturn(userDto);
        when(itemService.getById(anyLong())).thenReturn(itemDto);
        when(itemMapper.fromDto(any(ItemDto.class))).thenReturn(item);
        when(userMapper.fromDto(any(UserDto.class))).thenReturn(user);

        Booking booking = bookingMapper.fromDto(bookingDtoInput, 1L);

        assertEquals(booking.getStart(), bookingDtoInput.getStart());
        assertEquals(booking.getEnd(), bookingDtoInput.getEnd());
        assertEquals(booking.getItem(), item);
        assertEquals(booking.getBooker(), user);
        assertEquals(booking.getStatus(), BookingStatus.WAITING);
        assertEquals(bookingDtoInput.getStart(), booking.getStart());
    }

    @Test
    public void testToShortDto() {
        BookingDtoShort dto = bookingMapper.toShortDto(booking);

        assertEquals(booking.getId(), dto.getId());
        assertEquals(booking.getBooker().getId(), dto.getBookerId());
        assertEquals(booking.getStart(), dto.getStartTime());
        assertEquals(booking.getEnd(), dto.getEndTime());
    }
}
