package com.kh.matzip.review.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
import com.kh.matzip.util.file.FileService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/review")
public class ReviewController {

    private final ReviewService reviewService;
    private final FileService fileService;

    // 내 리뷰 리스트 조회 // 페이지네이션 글 5개부터 조회
    // authenticationprincipal 에서 user뽑아서 사용
    @GetMapping("/myReviewList")
    public ResponseEntity<Map<String, Object>> getMyReviews(
        @AuthenticationPrincipal CustomUserDetails user,
        @RequestParam(name = "page", defaultValue = "0") int page,
        @RequestParam(name = "size", defaultValue = "5") int size) {
        
        Map<String, Object> result = reviewService.selectReviewMyList(user.getUserNo(), page, size);
        return ResponseEntity.ok(result);
    }
        
    // 내 리뷰 상세 조회
    @GetMapping("/myReviewlist/{reviewNo}")
    public ResponseEntity<ReviewDTO> getReviewDetail(
        @AuthenticationPrincipal CustomUserDetails user,
        @PathVariable Long reviewNo){

            ReviewDTO dto = reviewService.selectMyReviewDeatil(user.getUserNo(), reviewNo);
            return ResponseEntity.ok(dto);
        }
    
    // 가게디테일에서 리뷰 리스트 뽑아내기
    @GetMapping("/store/{storeNo}")
    public ResponseEntity<List<ReviewDTO>> getStoreReviews(@PathVariable Long storeNo){
        return ResponseEntity.ok(reviewService.selectReviewDetail(storeNo));
    }


    // 리뷰 작성
    @PostMapping
    public ResponseEntity<String> writeReview(
        @AuthenticationPrincipal CustomUserDetails user,
        @RequestPart("data") @Valid ReviewWriteFormDTO form,
        @RequestPart(name = "files", required = false) List<MultipartFile> files) {

        if (files != null && files.size() > 5) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("이미지는 최대 5장까지만 업로드 가능합니다.");
        }

        List<String> urls = new ArrayList<>();
        if (files != null) {
            for (MultipartFile file : files) {
                urls.add(fileService.store(file));
            }
        }

        reviewService.insertReview(user.getUserNo(), form, urls);
        return ResponseEntity.status(HttpStatus.CREATED).body("리뷰 작성 완료");
    }

        
    // 리뷰뷰 수정정
    @PutMapping("/{reviewNo}")
    public ResponseEntity<String> updateReview(
        @AuthenticationPrincipal CustomUserDetails user,
        @PathVariable Long reviewNo,
        @RequestPart("data") @Valid ReviewWriteFormDTO form,
        @RequestPart(name = "files", required = false) List<MultipartFile> files) {

        if (files != null && files.size() > 5) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("이미지는 최대 5장까지만 업로드 가능합니다.");
        }

        List<String> urls = new ArrayList<>();
        if (files != null) {
            for (MultipartFile file : files) {
                urls.add(fileService.store(file));
            }
        }

        reviewService.updateReview(user.getUserNo(), reviewNo, form, urls);
        return ResponseEntity.ok("리뷰 수정 완료");
    }

    // 리뷰뷰 삭제제
    @DeleteMapping("/{reviewNo}")
    public ResponseEntity<String> deleteReview(
        @AuthenticationPrincipal CustomUserDetails user,
        @PathVariable Long reviewNo) {

        reviewService.deleteReview(user.getUserNo(), reviewNo);
        return ResponseEntity.ok("리뷰 삭제 완료");
    }
}


