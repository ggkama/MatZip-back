package com.kh.matzip.review.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.kh.matzip.review.model.dto.ReviewDTO;
import com.kh.matzip.review.model.dto.ReviewWriteFormDTO;

public interface ReviewService {

    Map<String, Object> selectMyReviewList(Long userNo, int pageNo, int size);

    List<ReviewDTO> selectMyReviewDetail(Long reviewNo);

    List<ReviewDTO> selectReviewDetail(Long storeNo);

    void insertReview(Long userNo, ReviewWriteFormDTO form, List<MultipartFile> files);

    void updateReview(Long userNo, Long reviewNo, ReviewWriteFormDTO form, List<MultipartFile> files);

    void deleteReview(Long userNo, Long reviewNo);
}
