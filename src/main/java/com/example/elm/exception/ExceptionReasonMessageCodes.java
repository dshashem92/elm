package com.example.elm.exception;

import lombok.Getter;

@Getter
public enum ExceptionReasonMessageCodes {
    INTERNAL_ERROR(0),
    NOT_FOUND_ERROR(1),

    CUSTOMER_INVALID_INPUT_ERROR(2);


    private final int value;

    ExceptionReasonMessageCodes(int value) {
        this.value = value;
    }
}
