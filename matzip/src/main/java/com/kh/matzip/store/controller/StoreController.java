package com.kh.matzip.store.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kh.matzip.member.model.vo.CustomUserDetails;
import com.kh.matzip.store.model.dto.StoreDTO;
import com.kh.matzip.store.model.service.StoreService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/owner/stores")
@RequiredArgsConstructor
public class StoreController {

 private final StoreService storeService;
    
    
    @PostMapping
    public ResponseEntity<String> insertStore(
            @AuthenticationPrincipal CustomUserDetails user,  // 인증된 사용자 정보
            @RequestPart("storeDto") StoreDTO storeDto,  // JSON 데이터 받기 (FormData)
            @RequestPart("images") MultipartFile[] images  // 파일 배열 받기 (FormData)
    ) {
        // 서비스로 전달하여 매장 등록
        CustomUserDetails user = null;
        storeService.insertStore(user, storeDto, images);
        return ResponseEntity.ok("매장이 등록되었습니다.");
    }

   @GetMapping("/api/owner/store/check")
    public ResponseEntity<Boolean> checkStoreExists(@RequestParam Long userNo) {
        boolean exists = storeService.existsStoreByUserNo(userNo);
        return ResponseEntity.ok(exists);
    }
    


}
