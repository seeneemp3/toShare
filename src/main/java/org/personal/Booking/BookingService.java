package org.personal.Booking;

import org.personal.Booking.dto.BookingDto;
import org.personal.Booking.dto.BookingDtoInput;
import org.personal.Booking.dto.BookingDtoShort;
import org.personal.Item.Item;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface BookingService {
    BookingDto create(BookingDtoInput bookingDto, Long bookerId);

    BookingDto update(Long bookingId, Long userId, Boolean approved);

    BookingDto getBookingById(Long bookingId, Long userId);

    List<BookingDto> getAllBookingByUser(Long userId, String state);

    List<BookingDto> getAllBookingByOwner(Long ownerId, String state);

    Map<Item, List<BookingDtoShort>> getLastAndNextBooking(Long ownerId);
}
