package org.personal.request.service;

import lombok.NoArgsConstructor;
import org.personal.exeption.InvalidInputDataException;
import org.springframework.data.domain.PageRequest;

@NoArgsConstructor
public class PageRequestMapper {

    public static PageRequest pageRequestValidaCreate(Integer from, Integer size) {
        if (from == null || size == null) {
            return null;
        }
        if (size <= 0 || from < 0) {
            throw new InvalidInputDataException("Incorrect from or size");
        }
        int page = from / size;
        return PageRequest.of(page, size);
    }
}
