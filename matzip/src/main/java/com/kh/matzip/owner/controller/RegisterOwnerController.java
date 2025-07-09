package com.kh.matzip.owner.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kh.matzip.global.enums.ResponseCode;
import com.kh.matzip.global.response.ApiResponse;
import com.kh.matzip.member.model.vo.CustomUserDetails;
import com.kh.matzip.owner.model.dto.RegisterOwnerDTO;
import com.kh.matzip.owner.model.service.RegisterOwnerService;
import com.kh.matzip.util.file.FileService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/mypage")
@RequiredArgsConstructor
public class RegisterOwnerController {

    private final RegisterOwnerService registerOwnerService;
    private final FileService fileService;

    @PostMapping("/registerOwner")
    public ResponseEntity<ApiResponse<Void>> registerOwner(
    		@ModelAttribute RegisterOwnerDTO registerOwnerDTO,
            @RequestParam (name="file", required=false) MultipartFile file,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        registerOwnerDTO.setUserNo(userDetails.getUserNo());
        
        String imagePath = fileService.store(file);
        registerOwnerDTO.setImage(imagePath);
        
        registerOwnerService.registerOwner(registerOwnerDTO);

        return ResponseEntity.ok(
                ApiResponse.success(ResponseCode.OWNER_REGISTER, "사장님 권한 신청이 완료되었습니다.")
        );
    }
}