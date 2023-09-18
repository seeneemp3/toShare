package org.personal.exeption;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BookingDataException extends IllegalArgumentException {
    public BookingDataException(String message) {
        super(message);
        log.error(message);
    }
}
