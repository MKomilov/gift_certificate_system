package com.epam.esm.exception;

import lombok.Getter;

@Getter
public class BaseException extends Exception{
    private final String code;

    public BaseException(String message, String code) {
        super(message);
        this.code = code;
    }

    public BaseException(String message, String code, Throwable cause) {
        super(message, cause);
        this.code = code;
    }
}
