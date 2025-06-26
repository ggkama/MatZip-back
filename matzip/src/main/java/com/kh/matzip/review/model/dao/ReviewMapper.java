package com.kh.matzip.review.model.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.kh.matzip.review.model.dto.ReviewDTO;
import com.kh.matzip.review.model.vo.Review;

@Mapper
public interface ReviewMapper {
    
    // 내 리뷰 리스트 조회
    List<ReviewDTO> selectMyReviewList(Map<String, String> pageInfo);

    // 내 리뷰 상세 조회
    ReviewDTO selectMyReviewDetail(Long reviewNo);

    // 맛집페이지 리뷰 출력
    List<ReviewDTO> selectReviewDetail(Long storeNo);

    // 리뷰 작성
    int insertReview(Review review);

    // 리뷰 수정
    int updateReview(Review review);
    // 리뷰 삭제
    int deleteReview(Long reviewNo);

    // 글 전체 개수조회
    int selectReviewCount(Map<String, String> pageInfo);
}
