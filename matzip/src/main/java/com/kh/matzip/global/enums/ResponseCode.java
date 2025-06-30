package com.kh.matzip.global.enums;

public enum ResponseCode {
    SUCCESS("200"),
    NOT_FOUND("404"),
    BAD_REQUEST("400"),

    MAIL_CODE_SEND_SUCCESS("S200"),
    MAIL_CODE_SUCCESS("S201"),
    USER_INSERT("S202"),
    LOGIN_SUCCESS("S203"),
    
    DUPLICATED_ID("E100"),
    DUPLICATED_NICKNAME("E101"),
    MAIL_CODE_SEND_FAIL("E102"),
    MAIL_CODE_FAIL("E103"),
    MAIL_CODE_NULL("E108"),
    USER_TABLE_FAIL("E104"),
    INVALID_USERDATA("E105"),
    VERIFIED_TIMEOUT("E106"),
    VERIFIED_FAIL("E107"),
    
    STORE_INSERT("S300"),
    STORE_DUPLICATED("E300"),
    STORE_SAVE_FAIL("E301"),
    
    SERVER_ERROR("500");
   
    private final String code;

    ResponseCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}