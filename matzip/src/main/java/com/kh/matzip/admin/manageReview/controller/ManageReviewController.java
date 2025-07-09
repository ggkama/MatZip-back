package com.kh.matzip.admin.manageReview.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.matzip.admin.manageReview.model.service.ManageReviewService;
import com.kh.matzip.member.model.vo.CustomUserDetails;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/manage/reviews")
@RequiredArgsConstructor
public class ManageReviewController {

    private final ManageReviewService manageReviewService;

    @GetMapping
    public ResponseEntity<?> getStoreListByAdmin(
    		@AuthenticationPrincipal CustomUserDetails user,
    		@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size) {
    	
        Map<String, Object> result = manageReviewService.getReviewListByAdmin(page, size);
        return ResponseEntity.ok(result);
    }
}