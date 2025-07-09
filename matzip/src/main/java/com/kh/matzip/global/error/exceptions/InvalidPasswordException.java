package com.kh.matzip.global.error.exceptions;

import com.kh.matzip.global.enums.ResponseCode;

public class InvalidPasswordException extends RuntimeException {

    private final ResponseCode code;

    public InvalidPasswordException(ResponseCode code, String message) {
        super(message);
        this.code = code;
    }

    public ResponseCode getResponseCode() {
        return code;
    }
}
