package com.kh.matzip.review.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

@Slf4j
@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final FileService fileService;

    // 내 리뷰 리스트 조회
    @GetMapping("/myreview")
    public ResponseEntity<Map<String, Object>> getMyReviews(
        @AuthenticationPrincipal CustomUserDetails user,
        @RequestParam(name = "page", defaultValue = "0") int page,
        @RequestParam(name = "size", defaultValue = "5") int size
    ) {
        Map<String, Object> result = reviewService.selectMyReviewList(user.getUserNo(), page, size);
        return ResponseEntity.ok(result);
    }

    // 내 리뷰 상세 조회
    @GetMapping("/myreview/detail/{reviewNo}")
    public ResponseEntity<List<ReviewDTO>> getMyReviewDetail(
        @AuthenticationPrincipal CustomUserDetails user,
        @PathVariable Long reviewNo
    ) {
        // 자기리뷰인지 체크
        List<ReviewDTO> detail = reviewService.selectMyReviewDetailAuth(user.getUserNo(), reviewNo); 
        return ResponseEntity.ok(detail);
    }

    // 매장 상세페이지 리뷰 출력 
    @GetMapping("/store/{storeNo}")
    public ResponseEntity<List<ReviewDTO>> getStoreReviews(@PathVariable Long storeNo) {
        List<ReviewDTO> reviews = reviewService.selectReviewDetail(storeNo);
        return ResponseEntity.ok(reviews);
    }

    // 리뷰 작성
    @PostMapping("/write")
    public ResponseEntity<String> writeReview(
        @AuthenticationPrincipal CustomUserDetails user,
        @ModelAttribute @Valid ReviewWriteFormDTO form,
        @RequestParam(name = "files") List<MultipartFile> files
    ) {
        try {
            reviewService.insertReview(user.getUserNo(), form, files); // 권한체크 userno로
            return ResponseEntity.status(HttpStatus.CREATED).body("리뷰 작성 완료");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    // 리뷰 수정
    @PutMapping("/{reviewNo}")
    public ResponseEntity<String> updateReview(
        @AuthenticationPrincipal CustomUserDetails user,
        @PathVariable Long reviewNo,
        @ModelAttribute @Valid ReviewWriteFormDTO form,
        @RequestParam(name = "files", required = false) List<MultipartFile> files
    ) {
        try {
            reviewService.updateReview(user.getUserNo(), reviewNo, form, files); // 얘도 권한체크
            return ResponseEntity.ok("리뷰 수정 완료");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    // 리뷰 삭제
    @DeleteMapping("/delete/{reviewNo}")
    public ResponseEntity<String> deleteReview(
        @AuthenticationPrincipal CustomUserDetails user,
        @PathVariable Long reviewNo
    ) {
        try {
            reviewService.deleteReview(user.getUserNo(), reviewNo); // 권한체크 추가
            return ResponseEntity.ok("리뷰 삭제 완료");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }
}
