package com.kh.matzip.member.model.dao;

import org.apache.ibatis.annotations.Mapper;

import com.kh.matzip.member.model.dto.MemberDTO;

@Mapper
public interface MemberMapper {
    
    int existByUserId(String userId);

    int existByUserNickName(String userNickName);

    int signUp(MemberDTO member);
    
    MemberDTO findByUserId(String userId);
  
}
