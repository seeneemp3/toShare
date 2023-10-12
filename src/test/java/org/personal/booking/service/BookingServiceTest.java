package org.personal.booking.service;

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
import org.personal.Item.comment.CommentMapper;
import org.personal.Item.comment.CommentRepository;
import org.personal.Item.dto.ItemDto;
import org.personal.Item.dto.ItemMapperImpl;
import org.personal.user.User;
import org.personal.user.UserRepository;
import org.personal.user.UserServiceImpl;
import org.personal.user.dto.UserDto;
import org.personal.user.dto.UserMapperImpl;
import org.personal.booking.Booking;
import org.personal.booking.BookingRepository;
import org.personal.booking.BookingServiceImpl;
import org.personal.booking.BookingStatus;
import org.personal.booking.dto.BookingDto;
import org.personal.booking.dto.BookingDtoInput;
import org.personal.booking.dto.BookingMapper;
import org.personal.exeption.BookingDataException;
import org.personal.exeption.BookingNotFoundException;
import org.personal.request.Request;
import org.personal.request.RequestRepository;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class BookingServiceTest {
    @Mock
    private ItemServiceImpl itemService;
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
    @InjectMocks
    private BookingServiceImpl bookingService;
    @Mock
    private BookingMapper bookingMapper;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private CommentMapper commentMapper;
    @Mock
    private RequestRepository requestRepository;
    private User user;
    private User user2;
    private UserDto userDto;
    private UserDto userDto2;
    private Item item;
    private ItemDto itemDto;
    private Booking booking;
    private BookingDtoInput bookingDtoInput;
    private BookingDto bookingDto;
    private Request request;
    private CommentDto commentDto;

    @BeforeEach
    public void setup() {
        user = User.builder().id(1L).name("user").email("user@user.com").build();
        user2 = User.builder().id(2L).name("user2").email("user2@user.com").build();
        userDto2 = new UserDto(2L, "user2", "user2@user.com");
        request = new Request(1L, "user2", user, LocalDateTime.now());
        item = new Item(1L, user, "item1", "item1", true, request.getId());
        booking = new Booking(1L, item,
                LocalDateTime.of(2023, Month.DECEMBER, 1, 12, 0),
                LocalDateTime.of(2023, Month.DECEMBER, 2, 12, 0),
                user, BookingStatus.APPROVED);
        bookingDto = new BookingDto(1L,
                LocalDateTime.of(2023, Month.DECEMBER, 2, 12, 0),
                LocalDateTime.of(2023, Month.DECEMBER, 1, 12, 0),
                item, user, BookingStatus.APPROVED);
        bookingDtoInput = new BookingDtoInput(1L, LocalDateTime.of(2023, Month.DECEMBER, 1, 12, 0),
                LocalDateTime.of(2023, Month.FEBRUARY, 2, 12, 0));
        commentDto = new CommentDto(1L, "commentText", item, user.getName(), LocalDateTime.now());
        itemDto = new ItemDto(1L, user, "item1", "item1", true, List.of(commentDto), request.getId());
        userDto = new UserDto(1L, "user", "user@user.com");

        when(bookingMapper.fromDto(any(), any())).thenReturn(booking);
        when(bookingMapper.toDto(any())).thenReturn(bookingDto);
        when(itemService.getById(any())).thenReturn(itemDto);
        when(itemMapper.fromDto(any())).thenReturn(item);
        when(bookingRepository.save(any())).thenReturn(booking);
        when(bookingRepository.findById(any())).thenReturn(Optional.of(booking));
        when(userService.getUserById(any())).thenReturn(userDto2);
        when(userMapper.fromDto(any())).thenReturn(user2);
    }

    @Test
    void testCreate() {
        BookingDto result = bookingService.create(bookingDtoInput, 1L);

        assertNotNull(result);
        verify(bookingRepository).save(any());
    }
    @Test
    void testUpdate() {
        assertThrows(BookingDataException.class, () -> bookingService.update(1L, 1L, true));
        BookingDto result = bookingService.update(1L, 1L, false);
        bookingDto.setStatus(BookingStatus.REJECTED);

        assertNotNull(result);
        verify(bookingRepository).save(any());
    }
    @Test
    void testGetBookingById(){
        BookingDto result = bookingService.getBookingById(booking.getId(), 1L);
        assertNotNull(result);

        when(bookingRepository.findById(3L)).thenReturn(Optional.empty());
        assertThrows(BookingNotFoundException.class, () -> {
            bookingService.getBookingById(3L, 1L);
        });
    }
    @Test
    void getAllBookingsByUser(){
        List<Booking> bookings = Arrays.asList(booking);

        when(bookingRepository.findAllByBooker_IdOrderByStartDesc(user.getId())).thenReturn(bookings);
        when(bookingRepository.findAllByBooker_IdAndStartBeforeAndEndAfterOrderByStartDesc(user.getId(), LocalDateTime.now(), LocalDateTime.now())).thenReturn(bookings);
        bookingDto.setStart(LocalDateTime.of(2022, Month.DECEMBER, 1, 12, 0));
        bookingDto.setEnd(LocalDateTime.of(2023, Month.DECEMBER, 2, 12, 0));

        when(bookingRepository.findAllByBooker_IdAndEndBeforeOrderByStartDesc(user.getId(), LocalDateTime.now())).thenReturn(bookings);
        bookingDto.setStart(LocalDateTime.of(2022, Month.DECEMBER, 1, 12, 0));
        bookingDto.setEnd(LocalDateTime.of(2022, Month.DECEMBER, 2, 12, 0));

        when(bookingRepository.findAllByBooker_IdAndStartAfterOrderByStartDesc(user.getId(), LocalDateTime.now())).thenReturn(bookings);
        bookingDto.setStart(LocalDateTime.of(2023, Month.DECEMBER, 1, 12, 0));
        bookingDto.setEnd(LocalDateTime.of(2023, Month.DECEMBER, 2, 12, 0));

        when(bookingRepository.findAllByBooker_IdAndStatusOrderByStart(user.getId(), BookingStatus.WAITING)).thenReturn(bookings);
        when(bookingRepository.findAllByBooker_IdAndStatusOrderByStart(user.getId(), BookingStatus.REJECTED)).thenReturn(bookings);

        List<BookingDto> resultAll = bookingService.getAllBookingByUser(user.getId(), "ALL");
        assertEquals(bookings.size(), resultAll.size());

        // CURRENT
        List<BookingDto> resultCurrent = bookingService.getAllBookingByUser(user.getId(), "CURRENT");
        assertEquals(bookings.size(), resultCurrent.size());

        // PAST
        List<BookingDto> resultPast = bookingService.getAllBookingByUser(user.getId(), "PAST");
        assertEquals(bookings.size(), resultPast.size());

        // FUTURE
        List<BookingDto> resultFuture = bookingService.getAllBookingByUser(user.getId(), "FUTURE");
        assertEquals(bookings.size(), resultFuture.size());

        // WAITING
        List<BookingDto> resultWaiting = bookingService.getAllBookingByUser(user.getId(), "WAITING");
        assertEquals(bookings.size(), resultWaiting.size());

        // REJECTED
        List<BookingDto> resultRejected = bookingService.getAllBookingByUser(user.getId(), "REJECTED");
        assertEquals(bookings.size(), resultRejected.size());

        // Invalid state
        assertThrows(BookingDataException.class, () -> {
            bookingService.getAllBookingByUser(user.getId(), "INVALID");
        });
    }

}
