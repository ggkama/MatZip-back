package com.kh.matzip.review.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.kh.matzip.global.enums.ResponseCode;
import com.kh.matzip.global.error.exceptions.InvalidAccessException;
import com.kh.matzip.global.error.exceptions.InvalidValueException;
import com.kh.matzip.global.error.exceptions.NoticeNotFoundException;
import com.kh.matzip.review.model.dao.ReviewMapper;
import com.kh.matzip.review.model.dto.ReviewDTO;
import com.kh.matzip.review.model.dto.ReviewWriteFormDTO;
import com.kh.matzip.review.model.vo.Review;
import com.kh.matzip.util.pagenation.PagenationService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewMapper reviewMapper;
    private final PagenationService pagenation;

    // ReservationMapper 불러와야함!
    // private final reservationService reservationService;


    @Override
    public Map<String, Object> selectReviewMyList(Long userNo, int pageNo, int size) {
        if (pageNo < 0 || size < 1) {
            throw new InvalidValueException(ResponseCode.BAD_REQUEST, "pageNo 또는 size가 잘못되었습니다.");
        }

        int startIndex = pagenation.getStartIndex(pageNo, size);

        Map<String, String> pageInfo = new HashMap<>();
        pageInfo.put("startIndex", String.valueOf(startIndex));
        pageInfo.put("size", String.valueOf(size));
        pageInfo.put("userNo", String.valueOf(userNo));

        List<ReviewDTO> list = reviewMapper.selectMyReviewList(pageInfo);
        int totalCount = reviewMapper.selectReviewCount(pageInfo);

        Map<String, Object> result = new HashMap<>();
        result.put("reviewList", list);
        result.put("totalCount", totalCount);
        return result;
    }

    @Override
    public ReviewDTO selectMyReviewDetail(Long userNo, Long reviewNo) {
        ReviewDTO dto = reviewMapper.selectMyReviewDetail(reviewNo);
        if (dto == null) {
            throw new NoticeNotFoundException(ResponseCode.NOT_FOUND, "해당 리뷰를 찾을 수 없습니다.");
        }
        if (!dto.getUserNo().equals(userNo)) {
            throw new InvalidAccessException(ResponseCode.UNAUTHORIZED, "본인의 리뷰만 조회할 수 있습니다.");
        }
        return dto;
    }

    @Override
    public List<ReviewDTO> selectReviewDetail(Long storeNo) {
        return reviewMapper.selectReviewDetail(storeNo);
    }

    @Transactional
    @Override
    public void insertReview(Long userNo, ReviewWriteFormDTO form, List<String> urls) {
        if (!reservationService.isReviewable(userNo, form.getReservationNo())) {
            throw new InvalidAccessException(ResponseCode.FORBIDDEN, "리뷰 작성가능한 권한이 없습니다.");
        }

        Review review = Review.builder()
                .reservationNo(form.getReservationNo())
                .userNo(userNo)
                .reviewContent(form.getReviewContent())
                .storeGrade(form.getStoreGrade())
                .build();

        reviewMapper.insertReview(review);

        if (urls != null) {
            for (String url : urls) {
                reviewMapper.insertReviewImage(review.getReviewNo(), url);
            }
        }
    }

    @Transactional
    @Override
    public void updateReview(Long userNo, Long reviewNo, ReviewWriteFormDTO form, List<String> urls) {
        ReviewDTO existing = reviewMapper.selectMyReviewDetail(reviewNo);
        if (existing == null) {
            throw new NoticeNotFoundException(ResponseCode.NOT_FOUND, "리뷰를 찾을 수 없습니다.");
        }

        if (!existing.getUserNo().equals(userNo)) {
            throw new InvalidAccessException(ResponseCode.UNAUTHORIZED, "본인의 리뷰만 수정할 수 있습니다.");
        }

        Review review = Review.builder()
                .reviewNo(reviewNo)
                .reviewContent(form.getReviewContent())
                .storeGrade(form.getStoreGrade())
                .build();

        reviewMapper.updateReview(review);

        if (urls != null) {
            reviewMapper.deleteReviewImages(reviewNo); // 기존 이미지 삭제 처리 
            for (String url : urls) {
                reviewMapper.insertReviewImage(reviewNo, url);
            }
        }
    }

    @Transactional
    @Override
    public void deleteReview(Long userNo, Long reviewNo) {
        ReviewDTO dto = reviewMapper.selectMyReviewDetail(reviewNo);
        if (dto == null) {
            throw new NoticeNotFoundException(ResponseCode.NOT_FOUND, "리뷰를 찾을 수 없습니다.");
        }

        if (!dto.getUserNo().equals(userNo)) {
            throw new InvalidAccessException(ResponseCode.UNAUTHORIZED, "본인의 리뷰만 삭제할 수 있습니다.");
        }

        reviewMapper.deleteReviewImages(reviewNo); // 연관 이미지 먼저 삭제
        reviewMapper.deleteReview(reviewNo);
    }
}


// 이미지 url로 받는지 다시 확인