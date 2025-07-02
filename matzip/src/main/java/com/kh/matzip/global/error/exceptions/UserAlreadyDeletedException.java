package com.kh.matzip.global.error.exceptions;

import com.kh.matzip.global.enums.ResponseCode;
import lombok.Getter;

@Getter
public class UserAlreadyDeletedException extends RuntimeException {
    
    private final ResponseCode responseCode;

    public UserAlreadyDeletedException(ResponseCode responseCode, String message) {
        super(message);
        this.responseCode = responseCode;
    }
}
