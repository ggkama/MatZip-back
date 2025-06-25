package com.kh.matzip.member.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.matzip.global.enums.ResponseCode;
import com.kh.matzip.global.response.ApiResponse;
import com.kh.matzip.member.model.dto.MemberDTO;
import com.kh.matzip.member.model.service.MemberService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class MemberController {
    
    private final MemberService memberService;
    
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody @Valid MemberDTO member){

        memberService.signUp(member);

        Map<String, String> response = Map.of(
                "message", "회원가입이 성공적으로 완료되었습니다.",
                "userEmail", member.getUserId()
        );
		
		log.info("회원가입 성공: {}", member.getUserId());
		return ResponseEntity.ok(ApiResponse.success(ResponseCode.USER_INSERT, response, "회원가입이 성공적으로 완료되었습니다."));
    }
    
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Map<String, String>>> login(@RequestBody MemberDTO member){
    	Map<String, String> tokens = memberService.login(member);
    	
    	log.info("로그인 성공 : {}", member.getUserId());
    	return ResponseEntity.ok(ApiResponse.success(ResponseCode.LOGIN_SUCCESS, tokens, "로그인이 성공적으로 완료되었습니다."));
    }
    
}
