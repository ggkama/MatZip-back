package com.kh.matzip.mypage.controller;

import org.apache.tomcat.util.http.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kh.matzip.mypage.model.service.MyProfileService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;

import com.kh.matzip.global.enums.ResponseCode;
import com.kh.matzip.global.response.ApiResponse;
import com.kh.matzip.member.model.vo.CustomUserDetails;
import com.kh.matzip.mypage.model.dao.MyProfileMapper;
import com.kh.matzip.mypage.model.dto.MyProfileDTO;


@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class MyProfileController  {

    
    // 내 정보 조회
    private final MyProfileService myProfileService;

    
   @GetMapping("form")
    public ResponseEntity<MyProfileDTO> getMyInfo(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Long userNo = userDetails.getUserNo();
        MyProfileDTO dto = myProfileService.selectMyProfile(userNo);

        if (dto == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(dto);
    }

    // 기본 정보 + 이미지 수정
    @PutMapping("/update")
    public ResponseEntity<ApiResponse<Void>> updateProfile(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestPart("userName") String userName,
            @RequestPart("userNickname") String userNickname,
            @RequestPart("userPhone") String userPhone,
            @RequestPart(value = "profileImage", required = false) MultipartFile profileImage
    ) {
        Long userNo = userDetails.getUserNo();

        MyProfileDTO dto = new MyProfileDTO();
        dto.setUserNo(userNo);
        dto.setUserName(userName);
        dto.setUserNickname(userNickname);
        dto.setUserPhone(userPhone);

        myProfileService.updateMyProfile(dto, profileImage);

        return ResponseEntity.ok(ApiResponse.success(ResponseCode.SUCCESS, "정보 수정 완료"));
    }
}
