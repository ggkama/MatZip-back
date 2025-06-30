package com.kh.matzip.global.error.exceptions;

import com.kh.matzip.global.enums.ResponseCode;

public class NoticeNotFoundException extends RuntimeException{
    
    private final ResponseCode responseCode;

    public NoticeNotFoundException(ResponseCode responseCode, String message) {
        super(message);
        this.responseCode = responseCode;
    }

    public ResponseCode getResponseCode(){
        return responseCode;
    }
}
