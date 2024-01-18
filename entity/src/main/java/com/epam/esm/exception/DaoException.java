package com.epam.esm.exception;

public class DaoException extends BaseException{

    public DaoException(String message, String code) {
        super(message, code);
    }

    public DaoException(String message, String code, Throwable cause) {
        super(message, code, cause);
    }

}
