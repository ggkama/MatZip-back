package com.kh.matzip.member.model.dao;

import org.apache.ibatis.annotations.Mapper;

import com.kh.matzip.member.model.vo.Member;

@Mapper
public interface MemberMapper {

    int signUp(Member memeber);
    
    int existByUserId(String userId);

    int existByUserNickName(String userNickName);

}
