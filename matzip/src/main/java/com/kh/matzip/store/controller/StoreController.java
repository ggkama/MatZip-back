package com.kh.matzip.store.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    
   @PostMapping("/write")
    public ResponseEntity<String> insertStore(
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestPart("storeDto") StoreDTO storeDto,
            @RequestPart(value = "images", required = false) MultipartFile[] images
    ) {
        
        // 서비스로 전달하여 매장 등록
        storeService.insertStore(user, storeDto, images);
        return ResponseEntity.ok("매장이 등록되었습니다.");
    }

   @GetMapping("/check")
        public ResponseEntity<Boolean> checkStoreExists(@AuthenticationPrincipal CustomUserDetails user) {
            boolean exists = storeService.existsStoreByUserNo(user.getUserNo());
            return ResponseEntity.ok(exists);
        }

    @GetMapping
    public ResponseEntity<StoreDTO> getStoreInfo(@AuthenticationPrincipal CustomUserDetails user) {
        StoreDTO store = storeService.getStoreByUserNo(user.getUserNo());
        if (store == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(store);
    }

   @PutMapping("/update")
    public ResponseEntity<String> updateStore(
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestPart("storeDto") StoreDTO storeDto,
            @RequestPart(value = "images", required = false) MultipartFile[] images,
            @RequestPart(value = "deletedImagePaths", required = false) List<String> deletedImagePaths,
            @RequestPart(value = "changedOldImages", required = false) List<String> changedOldImages,
            @RequestPart(value = "changedNewImages", required = false) List<MultipartFile> changedNewImages
    ) {
        storeService.updateStore(
            user,
            storeDto,
            images,
            deletedImagePaths,
            changedOldImages,
            changedNewImages
        );
        return ResponseEntity.ok("매장이 수정되었습니다.");
    }


}