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
import com.kh.matzip.global.error.exceptions.FileStreamException;
import com.kh.matzip.global.error.exceptions.FileTypeNotAllowedException;
import com.kh.matzip.global.error.exceptions.InvalidAccessException;
import com.kh.matzip.global.error.exceptions.InvalidPasswordException;
import com.kh.matzip.global.error.exceptions.InvalidReservationException;
import com.kh.matzip.global.error.exceptions.InvalidValueException;
import com.kh.matzip.global.error.exceptions.NoticeNotFoundException;
import com.kh.matzip.global.error.exceptions.OAuthUserNotFoundException;
import com.kh.matzip.global.error.exceptions.ReviewAccessDeniedException;
import com.kh.matzip.global.error.exceptions.ReviewNotAllowedException;
import com.kh.matzip.global.error.exceptions.ReviewNotFoundException;
import com.kh.matzip.global.error.exceptions.StoreAlreadyExistsException;
import com.kh.matzip.global.error.exceptions.StoreSaveFailedException;
import com.kh.matzip.global.error.exceptions.UpdateFailedException;
import com.kh.matzip.global.error.exceptions.UserAlreadyDeletedException;
import com.kh.matzip.global.error.exceptions.UserNotFoundException;
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

    // 리뷰 작성 불가
    @ExceptionHandler(ReviewNotAllowedException.class)
    public ResponseEntity<ApiResponse<Void>> handleReviewNotAllowedException(ReviewNotAllowedException e) {
        return makeResponseEntity(ResponseCode.FORBIDDEN, e.getMessage(), HttpStatus.FORBIDDEN);
    }

    // 리뷰 접근 권한 없음
    @ExceptionHandler(ReviewAccessDeniedException.class)
    public ResponseEntity<ApiResponse<Void>> handleReviewAccessDeniedException(ReviewAccessDeniedException e) {
        return makeResponseEntity(ResponseCode.FORBIDDEN, e.getMessage(), HttpStatus.FORBIDDEN);
    }

    // 존재하지 않는 리뷰
    @ExceptionHandler(ReviewNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleReviewNotFoundException(ReviewNotFoundException e) {
        return makeResponseEntity(ResponseCode.NOT_FOUND, e.getMessage(), HttpStatus.NOT_FOUND);
    }

    // 파일명/확장자추출/저장오류/삭제 예외처리
    @ExceptionHandler(FileStreamException.class)
    public ResponseEntity<ApiResponse<Void>> handleFileStreamException(FileStreamException e) {
    return makeResponseEntity(ResponseCode.INTERNAL_SERVER_ERROR, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // .jpg 확장자 이외 확장자 업로드
    @ExceptionHandler(FileTypeNotAllowedException.class)
    public ResponseEntity<ApiResponse<Void>> handleFileTypeNotAllowedException(FileTypeNotAllowedException e) {
    return makeResponseEntity(ResponseCode.UNSUPPORTED_MEDIA_TYPE, e.getMessage(), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }
    
    // 이미 탈퇴한 사용자
    @ExceptionHandler(UserAlreadyDeletedException.class)
    public ResponseEntity<ApiResponse<Void>> handleUserAlreadyDeletedException(UserAlreadyDeletedException e) {
        return makeResponseEntity(e.getResponseCode(), e.getMessage(), HttpStatus.BAD_REQUEST);
    }

   // 이미 존재하는 매장
    @ExceptionHandler(StoreAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Void>> handleStoreAlreadyExistsException(StoreAlreadyExistsException e) {
        return makeResponseEntity(ResponseCode.ALREADY_EXIST_STORE, e.getMessage(), HttpStatus.CONFLICT); 
    }

    // 매장 저장 실패
    @ExceptionHandler(StoreSaveFailedException.class)
    public ResponseEntity<ApiResponse<Void>> handleStoreSaveFailedException(StoreSaveFailedException e) {
        return makeResponseEntity(ResponseCode.STORE_SAVE_FAIL, e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // 사용자 없음
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleUserNotFoundException(UserNotFoundException e) {
        return makeResponseEntity(ResponseCode.USER_NOT_FOUND, e.getMessage(), HttpStatus.NOT_FOUND);
    }

    // 사용자 정보 수정 실패
    @ExceptionHandler(UpdateFailedException.class)
    public ResponseEntity<ApiResponse<Void>> handleUpdateFailedException(UpdateFailedException e) {
        return makeResponseEntity(ResponseCode.USER_UPDATE_FAIL, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 비밀번호 불일치 예외
    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ApiResponse<Void>> handleInvalidPasswordException(InvalidPasswordException e) {
        return makeResponseEntity(ResponseCode.INVALID_PASSWORD, e.getMessage(), HttpStatus.BAD_REQUEST);
    }
    // 예약 요청 파라미터 오류
    @ExceptionHandler(InvalidReservationException.class)
    public ResponseEntity<ApiResponse<Void>> handleInvalidReservationException(InvalidReservationException e) {
        return makeResponseEntity(ResponseCode.INVALID_RESERVATION, e.getMessage(), HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(OAuthUserNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleOAuthUserNotFoundException(OAuthUserNotFoundException e) {
        return makeResponseEntity(ResponseCode.USER_NOT_FOUND, e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}