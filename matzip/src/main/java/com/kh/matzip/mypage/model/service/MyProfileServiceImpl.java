package com.kh.matzip.mypage.model.service;

import org.springframework.stereotype.Service;

import com.kh.matzip.mypage.model.dao.MyProfileMapper;
import com.kh.matzip.mypage.model.dto.MyProfileDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MyProfileServiceImpl implements MyProfileService {
    
    private final MyProfileMapper myProfileMapper;

    public MyProfileDTO selectMyProfile() {

       /*  MyProfileDTO myProfile = myProfileMapper.selectMyProfile(getCurrentUserNo);

        if(myProfile == null ) {
            throw new RuntimeException("내정보가 없습니다.");
        } */
        return null;
    }
    
}
