package com.kh.matzip.mypage.model.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.kh.matzip.mypage.model.dto.MyProfileDTO;

@Mapper
public interface MyProfileMapper {

   // 내 정보 조회
    MyProfileDTO selectMyProfile(Long userNo);

    // 기본 정보 수정 (반환값 통일: 수정된 행 수)
    int updateUserProfile(MyProfileDTO dto);

    // 프로필 이미지 존재 여부 확인 (null 반환 시 없다는 뜻)
    String findImageByUserNo(Long userNo);

    // 프로필 이미지 삽입
    void insertProfileImage(Map<String, Object> imageData);

    // 프로필 이미지 수정
    void updateProfileImage(Map<String, Object> imageData);

    // 현재 비밀번호 조회
    String findPasswordByUserNo(Long userNo);

    // 비밀번호 변경
    int updatePassword(Map<String, Object> param);

    // 회원 탈퇴 (IS_DELETED = 'Y')
    void deleteUser(Long userNo);

    // 사장님 탈퇴 시 매장 IS_DELETED = 'Y'
    int deleteStore(Long userNo);

    // 회원 권한 조회
    String getUserRole(Long userNo);
}
