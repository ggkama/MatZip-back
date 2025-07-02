package com.kh.matzip.global.error.exceptions;

import com.kh.matzip.global.enums.ResponseCode;

public class StoreSaveFailedException extends RuntimeException {

    private final ResponseCode responseCode;

    public StoreSaveFailedException() {
        super("매장 저장에 실패했습니다.");
        this.responseCode = ResponseCode.STORE_SAVE_FAIL;
    }

    public StoreSaveFailedException(String message) {
        super(message);
        this.responseCode = ResponseCode.STORE_SAVE_FAIL;
    }

    public StoreSaveFailedException(String message, Throwable cause) {
        super(message, cause);
        this.responseCode = ResponseCode.STORE_SAVE_FAIL;
    }

    public ResponseCode getResponseCode() {
        return responseCode;
    }
}