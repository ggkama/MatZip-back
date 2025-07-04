package com.kh.matzip.store.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.matzip.store.model.service.StoreService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/store")
@RequiredArgsConstructor
public class StorePublicController {
    private final StoreService storeService;

    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> getStoreList(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "15") int size,
        @RequestParam(required = false) String search
    ) {
        Map<String, Object> result = storeService.getStoreList(page, size, search);
        return ResponseEntity.ok(result);
    }

    // 상세조회 등도 여기에
}
