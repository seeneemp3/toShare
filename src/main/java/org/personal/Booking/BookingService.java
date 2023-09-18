package org.personal.Booking;

import org.personal.Booking.dto.BookingDto;
import org.personal.Booking.dto.BookingDtoInput;

import java.util.List;

public interface BookingService {
    BookingDto create(BookingDtoInput bookingDto, Long bookerId);

    BookingDto update(Long bookingId, Long userId, Boolean approved);

    BookingDto getBookingById(Long bookingId, Long userId);
    List<BookingDto> getAllBookingByUser(Long userId, String state);
    List<BookingDto> getAllBookingByOwner(Long ownerId, String state);


}
