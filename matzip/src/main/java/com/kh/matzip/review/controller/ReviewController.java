package com.kh.matzip.review.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

@Slf4j
@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final FileService fileService;
    

    //내 리뷰 리스트 조회
    @GetMapping("/myreview")
    public ResponseEntity<Map<String, Object>> getMyReviews(
        @AuthenticationPrincipal CustomUserDetails user,
        @RequestParam(name = "page", defaultValue = "0") int page,
        @RequestParam(name = "size", defaultValue = "5") int size
    ) {
        Map<String, Object> result = reviewService.selectMyReviewList(user.getUserNo(), page, size);
        return ResponseEntity.ok(result);
    }

    //내 리뷰 상세 조회
    @GetMapping("/myreview/{reviewNo}")
    public ResponseEntity<List<ReviewDTO>> getMyReviewDetail(
        @AuthenticationPrincipal CustomUserDetails user,
        @PathVariable Long reviewNo
    ) {
        List<ReviewDTO> detail = reviewService.selectMyReviewDetail(reviewNo);
        return ResponseEntity.ok(detail);
    }

    //storeDeatil에서 리뷰 조회
    @GetMapping("/store/{storeNo}")
    public ResponseEntity<List<ReviewDTO>> getStoreReviews(@PathVariable Long storeNo) {
        List<ReviewDTO> reviews = reviewService.selectReviewDetail(storeNo);
        return ResponseEntity.ok(reviews);
    }

    //리뷰 작성
    //@PreAuthorize("hasRole('USER')")
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/write")
    public ResponseEntity<String> writeReview(
        @AuthenticationPrincipal CustomUserDetails user,
        @RequestParam("reservationNo") Long reservationNo,
        @RequestParam("reviewContent") String reviewContent,
        @RequestParam("storeGrade") Double storeGrade,
        @RequestParam(name = "files", required = false) List<MultipartFile> files
        ) {
    if (files != null && files.size() > 5) {
        return ResponseEntity.badRequest().body("이미지는 최대 5장까지 업로드 가능합니다.");
    }

    ReviewWriteFormDTO form = ReviewWriteFormDTO.builder()
        .reservationNo(reservationNo)
        .reviewContent(reviewContent)
        .storeGrade(storeGrade)
        .build();

    reviewService.insertReview(user.getUserNo(), form, files);
    return ResponseEntity.status(HttpStatus.CREATED).body("리뷰 작성 완료");
}


    //리뷰 수정
    @PutMapping("/{reviewNo}")
    public ResponseEntity<String> updateReview(
        @AuthenticationPrincipal CustomUserDetails user,
        @PathVariable Long reviewNo,
        @RequestPart("data") @Valid ReviewWriteFormDTO form,
        @RequestPart(name = "files", required = false) List<MultipartFile> files
    ) {
        if (files != null && files.size() > 5) {
            return ResponseEntity.badRequest().body("이미지는 최대 5장까지 업로드 가능합니다.");
        }

        reviewService.updateReview(user.getUserNo(), reviewNo, form, files);
        return ResponseEntity.ok("리뷰 수정 완료");
    }

    //리뷰 삭제
    @DeleteMapping("/{reviewNo}")
    public ResponseEntity<String> deleteReview(
        @AuthenticationPrincipal CustomUserDetails user,
        @PathVariable Long reviewNo
    ) {
        reviewService.deleteReview(user.getUserNo(), reviewNo);
        return ResponseEntity.ok("리뷰 삭제 완료");
    }
}
