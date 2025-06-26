package com.kh.matzip.review.model.service;

import java.util.List;
import java.util.Map;

import com.kh.matzip.review.model.dto.ReviewDTO;
import com.kh.matzip.review.model.dto.ReviewWriteFormDTO;

public interface ReviewService {
    
    Map<String, Object> selectReviewMyList(int pageNo, int size);

    List<ReviewDTO> selectMyReviewDeatil(Long reviewNo);

    void insertReview(ReviewWriteFormDTO form);

    void updateReview(Long reviewNo, ReviewWriteFormDTO form);

    void deleteReview(Long reviewNo);

}
