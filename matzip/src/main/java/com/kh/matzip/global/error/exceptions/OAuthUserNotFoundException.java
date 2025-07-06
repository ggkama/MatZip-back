package com.kh.matzip.global.error.exceptions;

import java.util.Map;

import lombok.Getter;

@Getter
public class OAuthUserNotFoundException extends RuntimeException {
    private final String accessToken;
    private final Map<String, Object> kakaoUser;

    public OAuthUserNotFoundException(String accessToken, Map<String, Object> kakaoUser) {
        this.accessToken = accessToken;
        this.kakaoUser = kakaoUser;
    }
}
