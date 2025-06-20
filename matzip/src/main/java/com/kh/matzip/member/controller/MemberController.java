package com.kh.matzip.member.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.matzip.member.model.dto.MemberDTO;
import com.kh.matzip.member.model.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class MemberController {
    
    private final MemberService memberService;

    public ResponseEntity<?> signUp(@RequestBody @Validated MemberDTO member){

        memberService.signUp(member);

        Map<String, String> response = Map.of(
                "message", "회원가입이 성공적으로 완료되었습니다.",
                "userEmail", member.getUserId()
        );
		
		
		log.info("회원가입 성공: {}", member.getUserId());
		return ResponseEntity.status(201).body(response);
    }
    
}
