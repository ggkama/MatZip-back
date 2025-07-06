package com.kh.matzip.mypage.model.service;

import org.springframework.web.multipart.MultipartFile;

import com.kh.matzip.mypage.model.dto.MyProfileDTO;

public interface MyProfileService {

   MyProfileDTO selectMyProfile(Long userNo);
    void updateMyProfile(MyProfileDTO dto, MultipartFile profileImage);
    /* void updateMyProfile(MyProfileDTO myProfile);

    void changePassword(PasswordDTO changePassword); */

    
    
}
