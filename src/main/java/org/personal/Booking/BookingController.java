package org.personal.Booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.personal.Booking.dto.BookingDto;
import org.personal.Booking.dto.BookingDtoInput;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/bookings")
public class BookingController {
    private static final String USER_ID = "X-Sharer-User-Id";
    private final BookingService service;

    @PostMapping
    public BookingDto create(@RequestBody BookingDtoInput bookingDtoInput,
                             @RequestHeader(USER_ID) Long bookerId) {
        return service.create(bookingDtoInput, bookerId);
    }

    @GetMapping("/{bookingId}")
    public BookingDto getBooking(@RequestHeader(USER_ID) Long userId,
                                 @PathVariable Long bookingId) {
        return service.getBookingById(bookingId, userId);
    }

    @GetMapping
    public List<BookingDto> getAllBookingByUser(@RequestHeader(USER_ID) Long userId,
                                                @RequestParam(defaultValue = "ALL") String state) {
        return service.getAllBookingByUser(userId, state);
    }

    @GetMapping("/owner")
    public List<BookingDto> getAllBookingByOwner(@RequestHeader(USER_ID) Long ownerId,
                                                 @RequestParam(defaultValue = "ALL") String state) {
        return service.getAllBookingByOwner(ownerId, state);
    }

    @PatchMapping("/{bookingId}")
    public BookingDto updateBookingApprove(@RequestHeader(USER_ID) Long ownerId,
                                           @PathVariable Long bookingId,
                                           @RequestParam Boolean approved) {
        return service.update(bookingId, ownerId, approved);
    }

}
