package com.kh.matzip.review.model.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import com.kh.matzip.review.model.dto.ReviewDTO;
import com.kh.matzip.review.model.vo.Review;
import com.kh.matzip.review.model.vo.ReviewImage;

@Mapper
public interface ReviewMapper {
    
    // 내 리뷰 리스트 조회
    List<ReviewDTO> selectMyReviewList(Map<String, String> pageInfo);

    // 내 리뷰 상세 조회 
    List<ReviewDTO> selectMyReviewDetail(Long reviewNo);

    // 맛집페이지 리뷰 출력
    List<ReviewDTO> selectReviewDetail(Long storeNo);

    // 리뷰 작성
    int insertReview(Review review);

    // 리뷰 수정
    int updateReview(Review review);

    // 리뷰 삭제
    int deleteReview(Long reviewNo);

    // 글 전체 개수조회
    long selectReviewCount(Map<String, String> pageInfo);

    // 이미지 등록
    int insertReviewImage(ReviewImage image);

    // 기존 이미지 삭제
    int deleteReviewImages(Long reviewNo);

    // 기존 이미지 URL 조회 하기(삭제용)
    List<String> selectReviewImageUrls(Long reviewNo);

    // 예약여부 체크
    boolean existsUserReservation(@Param("userNo") Long userNo, @Param("storeNo") Long storeNo, @Param("reservationNo") Long reservationNo);

    // 해당 예약에 이미 리뷰 썼는지
    boolean existsReviewByReservation(Long reservationNo);

    // 리뷰 작성자 userNo 반환
    ReviewDTO selectReviewOwner(Long reviewNo);

    
    

}
