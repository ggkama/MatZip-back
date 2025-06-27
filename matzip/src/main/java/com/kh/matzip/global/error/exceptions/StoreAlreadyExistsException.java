package com.kh.matzip.global.error.exceptions;

public class StoreAlreadyExistsException extends RuntimeException {

    public StoreAlreadyExistsException() {
        super("이미 등록된 매장입니다.");
    }

    public StoreAlreadyExistsException(String message) {
        super(message);
    }
}
