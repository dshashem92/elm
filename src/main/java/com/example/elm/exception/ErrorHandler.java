package com.example.elm.exception;


import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@RequiredArgsConstructor
@Order
@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    @ResponseBody
    public ResponseEntity<ApiError> handleAbstractBaseException(final AbstractBaseException exception) {
        log.warn(exception.getMessage(), exception);
        log.debug(exception.getMessage(), exception);

        final HttpStatusCode httpStatusCode = exception.getStatusCode();
        final ApiError apiError = new ApiError(
                httpStatusCode,
                exception.getMessageCode(),
                exception.toString()
        );

        return ResponseEntity.status(httpStatusCode).body(apiError);
    }

    @ExceptionHandler
    @ResponseBody
    public ResponseEntity<ApiError> handleUnexpectedException(final Exception exception) {
        final String exceptionMessage = "Uncategorized exception.";
        log.warn(exceptionMessage, exception);
        log.debug(exceptionMessage, exception);

        final HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        final ApiError apiError = new ApiError(
                httpStatus,
                ExceptionReasonMessageCodes.INTERNAL_ERROR.getValue(),
                exception.toString()
        );

        return ResponseEntity.status(httpStatus).body(apiError);
    }
}

