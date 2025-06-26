package com.kh.matzip.global.error.exceptions;

import com.kh.matzip.global.enums.ResponseCode;

public class DataAccessException extends RuntimeException {
    private ResponseCode code;

    public DataAccessException(ResponseCode code, String message) {
        super(message);
        this.code = code;
    }

    public ResponseCode getResponseCode() {
        return this.code;
    }
}