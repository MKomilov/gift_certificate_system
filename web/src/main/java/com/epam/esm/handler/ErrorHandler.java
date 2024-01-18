package com.epam.esm.handler;

import com.epam.esm.exception.BadRequestException;
import com.epam.esm.exception.BaseException;
import com.epam.esm.exception.DaoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(DaoException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<CustomResponse> handleDaoExceptions(DaoException e) {
        return buildResponseEntity(e);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<CustomResponse> handleBadRequestExceptions(BadRequestException e) {
        return buildResponseEntity(e);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<CustomResponse> handleGenericExceptions(Exception e) {
        CustomResponse customResponse = new CustomResponse(e.getMessage(), "500");
        return new ResponseEntity<>(customResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<CustomResponse> buildResponseEntity(BaseException e) {
        CustomResponse customResponse = new CustomResponse(e.getMessage(), e.getCode());
        return new ResponseEntity<>(customResponse, HttpStatus.valueOf(Integer.parseInt(e.getCode().substring(0, 3))));
    }
}
