package com.kh.matzip.mypage.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kh.matzip.global.enums.ResponseCode;
import com.kh.matzip.global.response.ApiResponse;
import com.kh.matzip.member.model.vo.CustomUserDetails;
import com.kh.matzip.mypage.model.dto.MyProfileDTO;
import com.kh.matzip.mypage.model.dto.PasswordDTO;
import com.kh.matzip.mypage.model.service.MyProfileService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


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

   @PutMapping("/password")
    public ResponseEntity<String> updatePassword(@Valid @RequestBody PasswordDTO passwordDTO) {
        CustomUserDetails userDetails = (CustomUserDetails)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userNo = userDetails.getUserNo();

        myProfileService.updatePassword(userNo, passwordDTO.getCurrentPw(), passwordDTO.getNewPw());

        return ResponseEntity.ok("비밀번호가 성공적으로 변경되었습니다.");
    }
    
    @PutMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestBody Map<String, String> payload) {
        CustomUserDetails userDetails = (CustomUserDetails)
            SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userNo = userDetails.getUserNo();
        String inputPw = payload.get("userPw");

        myProfileService.deleteUser(userNo, inputPw);

        return ResponseEntity.ok("회원 탈퇴가 완료되었습니다.");
    }


}
