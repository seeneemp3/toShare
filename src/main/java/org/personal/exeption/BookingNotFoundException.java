package org.personal.exeption;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BookingNotFoundException extends IllegalArgumentException {
    public BookingNotFoundException(String message) {
        super(message);
        log.error(message);
    }
}
