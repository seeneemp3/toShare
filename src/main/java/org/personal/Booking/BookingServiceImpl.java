package org.personal.Booking;

import lombok.RequiredArgsConstructor;
import org.personal.Booking.dto.BookingDto;
import org.personal.Booking.dto.BookingDtoInput;
import org.personal.Booking.dto.BookingDtoShort;
import org.personal.Booking.dto.BookingMapper;
import org.personal.Item.ItemService;
import org.personal.Item.dto.ItemMapper;
import org.personal.User.UserService;
import org.personal.User.dto.UserMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService{
    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final UserService userService;
    private final UserMapper userMapper;
    private final ItemMapper itemMapper;
    private final ItemService itemService;
    @Override
    public BookingDto create(BookingDtoInput bookingDto, Long bookerId) {
        Booking booking = bookingMapper.fromDto(bookingDto, bookerId);
        return null;
    }

    @Override
    public BookingDto update(Long bookingId, Long userId, Boolean approved) {
        return null;
    }

    @Override
    public BookingDto getBookingById(Long bookingId, Long userId) {
        return null;
    }

    @Override
    public List<BookingDto> getBookings(String state, Long userId) {
        return null;
    }

    @Override
    public List<BookingDto> getBookingsOwner(String state, Long userId) {
        return null;
    }

    @Override
    public BookingDtoShort getLastBooking(Long itemId) {
        return null;
    }

    @Override
    public BookingDtoShort getNextBooking(Long itemId) {
        return null;
    }

    @Override
    public Booking getBookingWithUserBookedItem(Long itemId, Long userId) {
        return null;
    }
}
