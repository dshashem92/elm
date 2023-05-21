package com.example.elm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class ApiError {

    private final LocalDateTime timestamp;

    private final HttpStatusCode status;

    private final int messageCode;

    private String debugMessage;

    public ApiError(
            final HttpStatusCode httpStatuscode,
            final int messageCode,
            final String debugMessage
    ) {
        this.timestamp = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);
        this.status = httpStatuscode;
        this.messageCode = messageCode;
        this.debugMessage = debugMessage;
    }
}
