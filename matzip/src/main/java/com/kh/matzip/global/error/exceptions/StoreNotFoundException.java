package com.kh.matzip.global.error.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class StoreNotFoundException extends ResponseStatusException {
    public StoreNotFoundException() {
        super(HttpStatus.NOT_FOUND, "등록된 매장이 없습니다.");
    }

    public StoreNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}