package com.kh.matzip.store.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.matzip.naversearchapi.NaverSearchApiService;
import com.kh.matzip.store.model.dto.StoreDTO;
import com.kh.matzip.store.model.service.StoreService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/store")
@RequiredArgsConstructor
public class StorePublicController {
    private final StoreService storeService;
    private final NaverSearchApiService naverSearchApiService;

    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> getStoreList(
        @RequestParam(name = "page", defaultValue = "0") int page,
        @RequestParam(name = "size", defaultValue = "15") int size,
        @RequestParam(name = "search", required = false) String search
    ) {
        Map<String, Object> result = storeService.getStoreList(page, size, search);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/detail/{storeNo}")
    public ResponseEntity<StoreDTO> getStoreDetail(@PathVariable(name = "storeNo") Long storeNo) {
        StoreDTO dto = storeService.getStoreDetail(storeNo);
    return ResponseEntity.ok(dto);
    }

    // 네이버 블로그 검색
    @GetMapping("/{storeName}/naver-blog")
    public ResponseEntity<String> getNaverBlog(@PathVariable String storeName) {
        
        System.out.println("storeName 넘어오냐?" + storeName);
            
        String result = naverSearchApiService.searchBlog(storeName);
        return ResponseEntity.ok(result);
    }

}
