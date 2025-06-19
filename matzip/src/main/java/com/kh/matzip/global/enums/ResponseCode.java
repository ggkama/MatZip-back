package com.kh.matzip.global.enums;

public enum ResponseCode {
    SUCCESS("200"),
    BAD_REQUEST("400"),
    SERVER_ERROR("500");

    private final String code;

    ResponseCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}