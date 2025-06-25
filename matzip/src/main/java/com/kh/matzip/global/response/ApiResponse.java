package com.kh.matzip.global.response;

import com.kh.matzip.global.enums.ResponseCode;

import lombok.Getter;

@Getter
public class ApiResponse<T> {
    
    private final boolean success;
    private final String code;
    private final T data;
    private final String message;

    private ApiResponse(boolean success, ResponseCode code, T data, String message) {
        this.success = success;
        this.code = code.getCode();
        this.data = data;
        this.message = message;
    }

    public static <T> ApiResponse<T> success(ResponseCode responseCode, T data, String message) {
        return new ApiResponse<>(true, responseCode, data, message);
    }

    public static <T> ApiResponse<T> success(ResponseCode responseCode, String message) {
        return new ApiResponse<>(true, responseCode, null, message);
    }

    public static <T> ApiResponse<T> error(ResponseCode responseCode, String message) {
        return new ApiResponse<>(false, responseCode, null, message);
    }

}
