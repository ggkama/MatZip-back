package com.kh.matzip.review.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kh.matzip.review.model.dao.ReviewMapper;
import com.kh.matzip.review.model.dto.ReviewDTO;
import com.kh.matzip.review.model.dto.ReviewWriteFormDTO;
import com.kh.matzip.review.model.vo.Review;
import com.kh.matzip.review.model.vo.ReviewImage;
import com.kh.matzip.util.file.FileService;
import com.kh.matzip.util.pagenation.PagenationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewMapper reviewMapper;
    private final FileService fileService;
    private final PagenationService pagenationService;

    @Override
    public Map<String, Object> selectMyReviewList(Long userNo, int pageNo, int size) {
        int startIndex = pagenationService.getStartIndex(pageNo, size);

        Map<String, String> pageInfo = new HashMap<>();
        pageInfo.put("userNo", userNo.toString());
        pageInfo.put("startIndex", String.valueOf(startIndex));
        pageInfo.put("size", String.valueOf(size));

        List<ReviewDTO> reviewList = reviewMapper.selectMyReviewList(pageInfo);
        int totalCount = reviewMapper.selectReviewCount(pageInfo);

        Map<String, Object> result = new HashMap<>();
        result.put("content", reviewList);
        result.put("totalCount", totalCount);
        return result;
    }

    @Override
    public List<ReviewDTO> selectMyReviewDetail(Long reviewNo) {
        return reviewMapper.selectMyReviewDetail(reviewNo);
    }

    @Override
    public List<ReviewDTO> selectReviewDetail(Long storeNo) {
        return reviewMapper.selectReviewDetail(storeNo);
    }

    @Override
    public void insertReview(Long userNo, ReviewWriteFormDTO form, List<MultipartFile> files) {
        // 1. Review insert
        Review review = new Review();
        review.setReservationNo(form.getReservationNo());
        review.setUserNo(userNo);
        review.setReviewContent(form.getReviewContent());
        review.setStoreGrade(form.getStoreGrade());

        reviewMapper.insertReview(review); // review_no는 DB에서 시퀀스로 생성됨

        // 2. 이미지 insert
        if (files != null) {
            for (MultipartFile file : files) {
                String url = fileService.store(file);
                ReviewImage image = new ReviewImage();
                image.setReviewNo(review.getReviewNo());
                image.setImage(url);
                reviewMapper.insertReviewImage(image);
            }
        }
    }

    @Override
    public void updateReview(Long userNo, Long reviewNo, ReviewWriteFormDTO form, List<MultipartFile> files) {
        // 1. 기존 리뷰 수정
        Review review = new Review();
        review.setReviewNo(reviewNo);
        review.setReviewContent(form.getReviewContent());
        review.setStoreGrade(form.getStoreGrade());

        reviewMapper.updateReview(review);

        // 2. 기존 이미지 삭제
        List<String> oldImages = reviewMapper.selectReviewImageUrls(reviewNo);
        for (String imageUrl : oldImages) {
            fileService.deleteFile(imageUrl);
        }
        reviewMapper.deleteReviewImages(reviewNo);

        // 3. 새 이미지 저장
        if (files != null) {
            for (MultipartFile file : files) {
                String url = fileService.store(file);
                ReviewImage image = new ReviewImage();
                image.setReviewNo(reviewNo);
                image.setImage(url);
                reviewMapper.insertReviewImage(image);
            }
        }
    }

    @Override
    public void deleteReview(Long userNo, Long reviewNo) {
        // 1. 이미지 파일 삭제
        List<String> urls = reviewMapper.selectReviewImageUrls(reviewNo);
        for (String url : urls) {
            fileService.deleteFile(url);
        }

        // 2. DB에서 이미지 삭제
        reviewMapper.deleteReviewImages(reviewNo);

        // 3. 리뷰 삭제
        reviewMapper.deleteReview(reviewNo);
    }
}
