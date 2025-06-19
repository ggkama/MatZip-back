package com.kh.matzip.global.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import com.kh.matzip.global.enums.ResponseCode;
import com.kh.matzip.global.error.exceptions.InvalidFormatException;
import com.kh.matzip.global.response.ApiResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private <T> ResponseEntity<ApiResponse<T>> makeResponseEntity(
            ResponseCode code,
            String message,
            HttpStatus httpStatus) {
        return ResponseEntity.status(httpStatus).body(ApiResponse.error(code, message));
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<ApiResponse<Void>> handleInvalidFormatException(InvalidFormatException e) {
        return makeResponseEntity(e.getResponseCode(), e.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
