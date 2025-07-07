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

     // 현재 비밀번호 가져오기
    String findPasswordByUserNo(Long userNo);

    // 비밀번호 변경
    int updatePassword(Map<String, Object> param);

    // 회원탈퇴
    void deleteUser(Long userNo);

    // 사장님탈퇴시 가게 isDelet - Y 처리 
    int deleteStore(Long userNo);

    String getUserRole(Long userNo);
}
