package com.kh.matzip.member.model.service;

import com.kh.matzip.member.model.dto.LoginDTO;
import com.kh.matzip.member.model.dto.MemberDTO;

public interface MemberService {
    
    /* 회원가입 */
    void signUp(MemberDTO memberDTO);
    
    /* 로그인 */
    LoginDTO login(MemberDTO memberDTO);


}
