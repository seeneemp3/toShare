package org.personal.Booking;

import org.personal.Booking.dto.BookingDto;
import org.personal.Booking.dto.BookingDtoInput;
import org.personal.Booking.dto.BookingDtoShort;

import java.util.List;

public interface BookingService {
    BookingDto create(BookingDtoInput bookingDto, Long bookerId);

    BookingDto update(Long bookingId, Long userId, Boolean approved);

    BookingDto getBookingById(Long bookingId, Long userId);

    List<BookingDto> getBookings(String state, Long userId);

    List<BookingDto> getBookingsOwner(String state, Long userId);

    BookingDtoShort getLastBooking(Long itemId);

    BookingDtoShort getNextBooking(Long itemId);

    Booking getBookingWithUserBookedItem(Long itemId, Long userId);
}
