package com.rostik.andrusiv.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class EventNotFoundException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "event not found";

    public EventNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public EventNotFoundException(String message) {
        super(message);
    }
}

