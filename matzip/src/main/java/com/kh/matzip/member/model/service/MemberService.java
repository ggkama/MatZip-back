package com.kh.matzip.member.model.service;

import java.util.Map;

import com.kh.matzip.member.model.dto.MemberDTO;

public interface MemberService {
    
    /* 회원가입 */
    void signUp(MemberDTO memberDTO);
    
    /* 로그인 */
    Map<String, String> login(MemberDTO memberDTO);

}
