package com.example.elm.exception;

import org.springframework.http.HttpStatus;

public class CustomerException extends AbstractBaseException  {

    public CustomerException(
            final String debugMessage,
            final ExceptionReasonMessageCodes messageCode,
            final HttpStatus httpStatus) {
        super(debugMessage, messageCode, httpStatus);
    }

}
