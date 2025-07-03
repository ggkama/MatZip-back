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
    LOGOUT_SUCESS("S204"),
    USER_DETAIL_SUCCESS("S205"),
    USER_UNREGISTER("S206"),
    
    DUPLICATED_ID("E100"),
    DUPLICATED_NICKNAME("E101"),
    MAIL_CODE_SEND_FAIL("E102"),
    MAIL_CODE_FAIL("E103"),
    USER_TABLE_FAIL("E104"),
    INVALID_USERDATA("E105"),
    VERIFIED_TIMEOUT("E106"),
    VERIFIED_FAIL("E107"),
    MAIL_CODE_NULL("E108"),
    ALREADY_DELETED("E109"),
    USER_NOT("E110"),
    USER_IS_DELETED("E111"),
    
    SERVER_ERROR("500"),

    STORE_SAVE_FAIL("S500"); // 매장 저장 실패를 위한 코드 추가
   
    private final String code;

    ResponseCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}