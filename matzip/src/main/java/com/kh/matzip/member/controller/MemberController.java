package com.kh.matzip.member.controller;

import java.util.Date;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.matzip.global.enums.ResponseCode;
import com.kh.matzip.global.response.ApiResponse;
import com.kh.matzip.member.model.dto.LoginDTO;
import com.kh.matzip.member.model.dto.MemberDTO;
import com.kh.matzip.member.model.service.MemberService;
import com.kh.matzip.member.model.service.TokenService;
import com.kh.matzip.member.model.vo.CustomUserDetails;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class MemberController {
    
    private final MemberService memberService;
    private final TokenService tokenService;
    
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
    public ResponseEntity<ApiResponse<LoginDTO>> login(@RequestBody MemberDTO member) {
        LoginDTO loginUser = memberService.login(member);

        log.info("로그인 성공 : {}", member.getUserId());

        return ResponseEntity.ok(ApiResponse.success(ResponseCode.LOGIN_SUCCESS, loginUser, "로그인이 성공적으로 완료되었습니다."));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<?>> logout(@AuthenticationPrincipal CustomUserDetails user) {
    	
        if (user == null) {
            log.warn("로그아웃 실패(로그인 정보가 없음");
        }

        tokenService.deleteToken(user.getUserNo());
        tokenService.deleteRefreshToken(new Date());

        log.info("로그아웃 처리 완료: userNo={}", user.getUserNo());

        return ResponseEntity.ok(ApiResponse.success(ResponseCode.LOGOUT_SUCESS, "로그아웃이 완료되었습니다."));
    }
    
    

}