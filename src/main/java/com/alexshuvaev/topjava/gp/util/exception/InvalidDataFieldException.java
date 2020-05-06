package com.alexshuvaev.topjava.gp.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class InvalidDataFieldException extends RuntimeException {
    public InvalidDataFieldException(String message) {
        super(message);
    }
}
