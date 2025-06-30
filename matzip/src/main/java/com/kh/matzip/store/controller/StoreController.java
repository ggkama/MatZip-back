package com.kh.matzip.store.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kh.matzip.global.enums.ResponseCode;
import com.kh.matzip.global.response.ApiResponse;
import com.kh.matzip.member.model.vo.CustomUserDetails;
import com.kh.matzip.store.model.dto.StoreDTO;
import com.kh.matzip.store.model.service.StoreService;

import org.springframework.security.core.Authentication;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/owner/stores")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> save(
            @Valid @RequestPart StoreDTO storeDto,
            @RequestPart(name = "images", required = false) MultipartFile[] images) {

        // 인증 정보에서 사용자 추출
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails user = (CustomUserDetails) auth.getPrincipal();

        // ROLE_OWNER인지 확인
        if (!"ROLE_OWNER".equals(user.getUserRole())) {
            throw new AccessDeniedException("사장님 계정(ROLE_OWNER)만 매장을 등록할 수 있습니다.");
        }

        storeDto.setUserNo(user.getUserNo());
        storeService.insertStore(storeDto, images);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(ResponseCode.STORE_INSERT, "매장이 성공적으로 등록되었습니다."));
    }

    @GetMapping
    public void abc() {
        System.err.println("열받네?");
    }
}