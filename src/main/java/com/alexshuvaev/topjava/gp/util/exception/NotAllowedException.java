package com.alexshuvaev.topjava.gp.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
public class NotAllowedException extends RuntimeException {
    public NotAllowedException(String message) {
        super(message);
    }
}