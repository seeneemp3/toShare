package org.personal.booking.dto;

import lombok.RequiredArgsConstructor;
import org.personal.Item.ItemServiceImpl;
import org.personal.Item.dto.ItemMapper;
import org.personal.User.UserServiceImpl;
import org.personal.User.dto.UserMapper;
import org.personal.booking.Booking;
import org.personal.booking.BookingStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookingMapper {
    private final UserServiceImpl userService;
    private final ItemServiceImpl itemService;
    private final UserMapper userMapper;
    private final ItemMapper itemMapper;

    public BookingDto toDto(Booking booking) {
        if (booking != null) {
            return new BookingDto(
                    booking.getId(),
                    booking.getStart(),
                    booking.getEnd(),
                    booking.getItem(),
                    booking.getBooker(),
                    booking.getStatus()
            );
        } else {
            return null;
        }
    }

    public Booking fromDto(BookingDtoInput bookingDtoInput, Long bookerId) {
        if (bookingDtoInput == null) {
            return null;
        }
        Booking b = new Booking();

        b.setItem(itemMapper.fromDto(itemService.getById(bookingDtoInput.getItemId())));
        b.setStart(bookingDtoInput.getStart());
        b.setEnd(bookingDtoInput.getEnd());
        b.setBooker(userMapper.fromDto(userService.getUserById(bookerId)));
        b.setStatus(BookingStatus.WAITING);

        return b;
    }

    public BookingDtoShort toShortDto(Booking booking) {
        if (booking != null) {
            return new BookingDtoShort(
                    booking.getId(),
                    booking.getBooker().getId(),
                    booking.getStart(),
                    booking.getEnd()
            );
        } else {
            return null;
        }
    }
}
