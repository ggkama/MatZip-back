package com.kh.matzip.mypage.model.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.kh.matzip.mypage.model.dto.MyProfileDTO;

@Mapper
public interface MyProfileMapper {

    MyProfileDTO selectMyProfile(Long userNo);

    void updateUserProfile(MyProfileDTO dto);

    String findImageByUserNo(Long userNo);

    void insertProfileImage(Map<String, Object> imageData);

    void updateProfileImage(Map<String, Object> imageData);

    void changePassword(Map<String, Object> changeRequest);
}
