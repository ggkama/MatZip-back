package com.kh.matzip.global.error.exceptions;

public class FileTypeNotAllowedException extends RuntimeException {
    public FileTypeNotAllowedException(String message) {
        super(message);
    }
}