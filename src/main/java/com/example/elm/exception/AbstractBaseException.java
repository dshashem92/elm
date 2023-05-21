package com.example.elm.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


public abstract class AbstractBaseException extends ResponseStatusException {

    private final ExceptionReasonMessageCodes messageCode;

    /**
     * @param debugMessage  The exception message
     * @param messageCode   The key of the user-friendly message
     * @param httpStatus    The HTTP status to render in response
     */
    public AbstractBaseException(
            final String debugMessage, final ExceptionReasonMessageCodes messageCode, final HttpStatus httpStatus) {
        super(httpStatus, debugMessage);
        this.messageCode = messageCode;
    }

    public int getMessageCode() {
        return messageCode.getValue();
    }
}
