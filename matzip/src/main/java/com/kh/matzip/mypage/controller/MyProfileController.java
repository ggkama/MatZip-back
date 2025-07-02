package com.kh.matzip.mypage.controller;

import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.kh.matzip.mypage.model.service.MyProfileService;
import com.kh.matzip.mypage.model.vo.MyProfile;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kh.matzip.mypage.model.dto.MyProfileDTO;


@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class MyProfileController  {
    

    // 내 정보 조회
    private final MyProfileService myProfileService;
    
    @GetMapping
    public ResponseEntity<MyProfileDTO> myProfile() {
        MyProfileDTO myProfile = myProfileService.selectMyProfile();
        return ResponseEntity.ok(myProfile);

        
    };



}
