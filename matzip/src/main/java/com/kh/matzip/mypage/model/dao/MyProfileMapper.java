package com.kh.matzip.mypage.model.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.kh.matzip.mypage.model.dto.MyProfileDTO;

@Mapper
public interface MyProfileMapper {
    MyProfileDTO selectMyprofile(Long userNo);

    void updateMyProfile(MyProfileDTO MyProfile);

    void changePassword(Map<String, Object> changeRequest);
}
