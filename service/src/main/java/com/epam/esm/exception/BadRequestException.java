package com.epam.esm.exception;

public class BadRequestException extends BaseException{

    public BadRequestException(String message, String code) {
        super(message, code);
    }

    public BadRequestException(String message, String code, Throwable cause) {
        super(message, code, cause);
    }
}
