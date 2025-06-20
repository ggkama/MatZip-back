package com.kh.matzip.member.model.service;

import org.springframework.stereotype.Service;

import com.kh.matzip.member.model.dao.MemberMapper;
import com.kh.matzip.member.model.dto.MemberDTO;
import com.kh.matzip.member.model.vo.Member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    
    private final MemberMapper memberMapper;

    @Override
    public void signUp(MemberDTO member) {
        int isIdDuplicated = memberMapper.existByUserId(member.getUserId());
        int isNickNameDuplicated = memberMapper.existByUserNickName(member.getUserNickName());

        if(isIdDuplicated > 0){
            System.out.println("이미 사용 중인 아이디입니다.");
        }

        if(isNickNameDuplicated > 0){
            System.out.println("이미 사용 중인 비밀번호입니다.");
        }

        Member memberValue = Member.builder()
                                   .userId(member.getUserId())
                                   .userPw(member.getUserPw())
                                   .userName(member.getUserName())
                                   .userNickName(member.getUserName())
                                   .userPhone(member.getUserPhone())
                                   .userRole("USER")
                                   .build();

        memberMapper.signUp(memberValue);
    }
}
