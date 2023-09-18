package org.personal.Booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.personal.Booking.BookingStatus;
import org.personal.Item.Item;
import org.personal.User.User;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class BookingDto {
    private Long id;
    private LocalDateTime start;
    private LocalDateTime end;
    private Item item;
    private User booker;
    private BookingStatus status;
}