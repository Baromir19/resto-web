package com.resto.pizzeria.web.exception;

import lombok.Getter;

import java.io.IOException;

@Getter
public final class ApiResponseException extends IOException {
    private final Integer code;
    private final String codeExtended;

    public ApiResponseException(
            final String message,
            final String codeExtended,
            final Integer code) {
        super(message);
        this.code = code;
        this.codeExtended = codeExtended;
    }

    public ApiResponseException(
            final String message,
            final Integer code) {
        this(message, "", code);
    }
}