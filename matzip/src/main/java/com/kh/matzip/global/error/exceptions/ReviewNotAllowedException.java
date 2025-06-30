package com.kh.matzip.global.error.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ReviewNotAllowedException extends RuntimeException{
    
    public ReviewNotAllowedException(String message){
        super(message);
    }
}
