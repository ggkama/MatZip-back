package com.kh.matzip.global.enums;

public enum ResponseCode {
    SUCCESS("200"),
    NOT_FOUND("404"),
    BAD_REQUEST("400"),
    FORBIDDEN("405"),
    INTERNAL_SERVER_ERROR("406"),
    UNSUPPORTED_MEDIA_TYPE("407"),
    

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
    
    SERVER_ERROR("500");
   
    private final String code;

    ResponseCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}