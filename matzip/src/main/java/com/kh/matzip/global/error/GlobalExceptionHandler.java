package com.kh.matzip.global.error;

import java.net.BindException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.kh.matzip.global.enums.ResponseCode;
import com.kh.matzip.global.error.exceptions.AuthenticateFailException;
import com.kh.matzip.global.error.exceptions.AuthenticateTimeOutException;
import com.kh.matzip.global.error.exceptions.DataAccessException;
import com.kh.matzip.global.error.exceptions.DuplicateDataException;
import com.kh.matzip.global.error.exceptions.InvalidAccessException;
import com.kh.matzip.global.error.exceptions.InvalidValueException;
import com.kh.matzip.global.error.exceptions.NoticeNotFoundException;
import com.kh.matzip.global.response.ApiResponse;

import jakarta.validation.ConstraintViolationException;
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

    // DB 서버 연결 실패
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ApiResponse<Void>> handleDataAccessException(DataAccessException e) {
        return makeResponseEntity(e.getResponseCode(), e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 인증 시간 초과
    @ExceptionHandler(AuthenticateTimeOutException.class)
    public ResponseEntity<ApiResponse<Void>> handleAuthenticateTimeOutException(AuthenticateTimeOutException e) {
        return makeResponseEntity(e.getResponseCode(), e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    // 인증 실패
    @ExceptionHandler(AuthenticateFailException.class)
    public ResponseEntity<ApiResponse<Void>> handleAuthenticateFailException(AuthenticateFailException e) {
        return makeResponseEntity(e.getResponseCode(), e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    // 접근 방식 오류
    @ExceptionHandler(InvalidAccessException.class)
    public ResponseEntity<ApiResponse<Void>> handleInvalidAccessException(InvalidAccessException e) {
        return makeResponseEntity(e.getResponseCode(), e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // 중복 검사 오류
    @ExceptionHandler(DuplicateDataException.class)
    public ResponseEntity<ApiResponse<Void>> handleDuplicateDataException(DuplicateDataException e) {
        return makeResponseEntity(e.getResponseCode(), e.getMessage(), HttpStatus.CONFLICT);
    }
    
    // 값이 조건에 불만족
    @ExceptionHandler(InvalidValueException.class)
    public ResponseEntity<ApiResponse<Void>> handleInvalidValueException(InvalidValueException e) {
        return makeResponseEntity(e.getResponseCode(), e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // Valid가 발생시키는 Exception(@Pattern, @NotBlank)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<Void>> handleConstraintViolationException(ConstraintViolationException e) {
        return makeResponseEntity(ResponseCode.BAD_REQUEST, e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // @Valid @RequestBody 에서 발생시키는 Exception
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return makeResponseEntity(ResponseCode.BAD_REQUEST, e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // @Valid @ModelAttribute 에서 발생시키는 Exception
    @ExceptionHandler(BindException.class)
    public ResponseEntity<ApiResponse<Void>> handleBindException(BindException e) {
        return makeResponseEntity(ResponseCode.BAD_REQUEST, e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // 연결실패 Exception
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<Void>> handleRuntimeException(RuntimeException e) {
        return makeResponseEntity(ResponseCode.SERVER_ERROR, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 삭제된 공지사항 조회 Exception
    @ExceptionHandler(NoticeNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleInvalidFormatException(NoticeNotFoundException e) {
        return makeResponseEntity(e.getResponseCode(), e.getMessage(), HttpStatus.NOT_FOUND);
    }
}