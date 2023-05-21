package com.example.elm.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends AbstractBaseException {

    private static final HttpStatus DEFAULT_STATUS = HttpStatus.NOT_FOUND;

    public NotFoundException(final String debugMessage,
                             final ExceptionReasonMessageCodes messageCode) {
        super(debugMessage, messageCode, DEFAULT_STATUS);
    }
}

