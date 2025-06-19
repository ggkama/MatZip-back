package com.kh.matzip.global.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.matzip.global.enums.ResponseCode;
import com.kh.matzip.global.error.exceptions.InvalidFormatException;
import com.kh.matzip.global.response.ApiResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/api")
public class GlobalController {
    
    /**
     * 반환 데이터가 없는 경우 
     * ApiResponse<Void> 로 반환 값을 지정합니다. Void인 이유는 <T> 타입이 data 필드의 타입이기 때문에
     * <Void>로 선언하면 data가 Null 이라는것을 의미합니다.
     * 
     * 반대로 data가 존재할 경우에는 data의 자료형을 써주어야합니다
     * 예) ApiResponse<String> / ApiResponse<MemberDTO> / ApiResponse<Map<String,Object>>
     * @return
     */
    @GetMapping
    public ResponseEntity<ApiResponse<Void>> getRoot() {
        return ResponseEntity.ok(ApiResponse.success(ResponseCode.SUCCESS, "요청 성공"));
    }
    
    @GetMapping("/error")
    public ResponseEntity<ApiResponse<Void>> getError() {
        throw new InvalidFormatException(ResponseCode.BAD_REQUEST, "입력한 값이 유효하지 않습니다.");
    }

}
