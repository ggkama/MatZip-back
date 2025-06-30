package com.kh.matzip.store.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import com.kh.matzip.store.model.dto.StoreDTO;
import com.kh.matzip.store.model.service.StoreService;
import com.kh.matzip.member.model.vo.CustomUserDetails;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/owner/stores")
@RequiredArgsConstructor
public class StoreController {

 private final StoreService storeService;

    @PostMapping
    public ResponseEntity<String> insertStore(
            @AuthenticationPrincipal CustomUserDetails user,  // @AuthenticationPrincipal을 사용해 인증된 사용자 정보를 자동으로 주입
            @RequestPart StoreDTO storeDto,
            @RequestPart MultipartFile[] images
    ) {
        storeService.insertStore(user, storeDto, images);  // StoreService로 전달
        return ResponseEntity.ok("매장이 등록되었습니다.");
    }


}
