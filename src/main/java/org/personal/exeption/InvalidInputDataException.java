package org.personal.exeption;

import lombok.extern.slf4j.Slf4j;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InvalidInputDataException extends IllegalArgumentException {
    public InvalidInputDataException(String message) {
        super(message);
        log.error(message);
    }
}
