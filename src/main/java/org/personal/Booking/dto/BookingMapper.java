package org.personal.Booking.dto;

import lombok.RequiredArgsConstructor;
import org.personal.Booking.Booking;
import org.personal.Booking.BookingStatus;
import org.personal.Item.ItemServiceImpl;
import org.personal.Item.dto.ItemMapper;
import org.personal.User.UserServiceImpl;
import org.personal.User.dto.UserMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookingMapper {
    private UserServiceImpl userService;
    private ItemServiceImpl itemService;
    private UserMapper userMapper;
    private ItemMapper itemMapper;

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
        return new Booking(
                null,
                itemMapper.dtoToItem(itemService.getById(bookingDtoInput.getItemId())),
                bookingDtoInput.getStart(),
                bookingDtoInput.getEnd(),
                userMapper.dtoToUser(userService.getUserById(bookerId)),
                BookingStatus.WAITING
        );
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
