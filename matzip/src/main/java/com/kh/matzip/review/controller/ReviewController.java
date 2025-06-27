package com.kh.matzip.review.controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kh.matzip.member.model.vo.CustomUserDetails;
import com.kh.matzip.review.model.dto.ReviewDTO;
import com.kh.matzip.review.model.dto.ReviewWriteFormDTO;
import com.kh.matzip.review.model.service.ReviewService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/review")
public class ReviewController {

    private final ReviewService reviewService;

    // 내 리뷰 리스트 조회
    @GetMapping("/myreviewlist")
    public ResponseEntity<Map<String,Object>> getMyReviewList(
        @RequestParam(defaultValue= = "0") int page,
        @RequestParam int size,
        Principal principal) {
            return ResponseEntity.ok(reviewService.selectMyReviewList(userDetails.getUserNo(), page,size));
        }
        
    // 내 리뷰 상세 조회
    @GetMapping("/myreviewlist/{reviewNo}")
    public ResponseEntity<ReviewDTO> getReviewDetail(
        @PathVariable Long reviewNo, Principal principal){
            List<ReviewDTO> detail = reviewService.selectMyReviewDeatil(reviewNo);
            return ResponseEntity.ok(detail.get(0));
        }
    
    // 리뷰 작성
    @PostMapping("/write")
    public ResponseEntity<?> insertReview(
        @RequestPart @Valid ReviewWriteFormDTO form,
        @RequestParam(required=false) List<MultipartFile> images,
        @AuthenticationPrincipal CustomUserDetails userDeatils) {

            if
        }   
        
    

}
