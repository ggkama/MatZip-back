package com.kh.matzip.global.error.exceptions;

import com.kh.matzip.global.enums.ResponseCode;

public class AuthenticateFailException extends RuntimeException{
    
    private ResponseCode code;

    public AuthenticateFailException(ResponseCode code, String message) {
        super(message);
        this.code = code;
    }

    public ResponseCode getResponseCode() {
        return code;
    }
}
