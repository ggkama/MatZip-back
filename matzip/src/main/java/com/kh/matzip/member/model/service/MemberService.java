package com.kh.matzip.member.model.service;

import com.kh.matzip.member.model.dto.MemberDTO;

public interface MemberService {
    
    /* 회원가입 */
    void signUp(MemberDTO memberDTO);

}
