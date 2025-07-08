package com.kh.matzip.review.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kh.matzip.global.error.exceptions.ReviewNotAllowedException;
import com.kh.matzip.global.error.exceptions.ReviewNotFoundException;
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

        // 각 리뷰에 이미지 URL 리스트 주입
        for (ReviewDTO review : reviewList) {
            List<String> imageUrls = reviewMapper.selectReviewImageUrls(review.getReviewNo());
            review.setImageUrls(imageUrls);
            // 대표 이미지(썸네일)로 첫 번째 이미지를 세팅(필요하다면)
            if (!imageUrls.isEmpty()) {
                review.setReviewImageUrl(imageUrls.get(0));
            } else {
                review.setReviewImageUrl(null);
            }
        }

        Long totalCount = reviewMapper.selectReviewCount(pageInfo);

        Map<String, Object> result = new HashMap<>();
        result.put("content", reviewList);
        result.put("totalCount", totalCount);
        return result;
    }

    @Override
    public List<ReviewDTO> selectMyReviewDetail(Long reviewNo) {
        List<ReviewDTO> detailList = reviewMapper.selectMyReviewDetail(reviewNo);

        for (ReviewDTO review : detailList) {
            List<String> imageUrls = reviewMapper.selectReviewImageUrls(review.getReviewNo());
            review.setImageUrls(imageUrls);
            if (!imageUrls.isEmpty()) {
                review.setReviewImageUrl(imageUrls.get(0));
            } else {
                review.setReviewImageUrl(null);
            }
        }

        return detailList;
    }

    @Override
    public List<ReviewDTO> selectMyReviewDetailAuth(Long userNo, Long reviewNo) {
        List<ReviewDTO> detailList = reviewMapper.selectMyReviewDetail(reviewNo);
        if (detailList.isEmpty() || !detailList.get(0).getUserNo().equals(userNo)) {
            throw new RuntimeException("본인만 접근 가능합니다."); // 권한 체크
        }
        for (ReviewDTO review : detailList) {
            List<String> imageUrls = reviewMapper.selectReviewImageUrls(review.getReviewNo());
            review.setImageUrls(imageUrls);
            if (!imageUrls.isEmpty()) {
                review.setReviewImageUrl(imageUrls.get(0));
            } else {
                review.setReviewImageUrl(null);
            }
        }
        return detailList;
    }

    @Override
    public List<ReviewDTO> selectReviewDetail(Long storeNo) {
        List<ReviewDTO> reviews = reviewMapper.selectReviewDetail(storeNo);
        for (ReviewDTO review : reviews) {
            List<String> imageUrls = reviewMapper.selectReviewImageUrls(review.getReviewNo());
            review.setImageUrls(imageUrls);
            if (!imageUrls.isEmpty()) {
                review.setReviewImageUrl(imageUrls.get(0));
            } else {
                review.setReviewImageUrl(null);
            }
        }
        return reviews;
    }

    @Override
    public void insertReview(Long userNo, ReviewWriteFormDTO form, List<MultipartFile> files) {
        boolean hasReservation = reviewMapper.existsUserReservation(userNo, form.getStoreNo(), form.getReservationNo());
        if (!hasReservation) throw new ReviewNotAllowedException("해당 가게 예약 내역이 없습니다.");

        int alreadyWritten = reviewMapper.existsReviewByReservation(form.getReservationNo());
        if (alreadyWritten > 0) throw new IllegalStateException("이미 해당 예약에 리뷰를 작성했습니다.");

        if (files == null || files.size() == 0)
            throw new IllegalArgumentException("이미지는 1장 이상 필수입니다.");

        Review review = new Review();
        review.setReservationNo(form.getReservationNo());
        review.setUserNo(userNo);
        review.setReviewContent(form.getReviewContent());
        review.setStoreGrade(form.getStoreGrade());
        review.setStoreNo(form.getStoreNo());

        reviewMapper.insertReview(review);

        for (MultipartFile file : files) {
            String url = fileService.store(file);
            ReviewImage image = new ReviewImage();
            image.setReviewNo(review.getReviewNo());
            image.setImage(url);
            reviewMapper.insertReviewImage(image);
        }
    }

    @Override
    public void updateReview(Long userNo, Long reviewNo, ReviewWriteFormDTO form, List<MultipartFile> files) {
        ReviewDTO owner = reviewMapper.selectReviewOwner(reviewNo);
        if (owner == null) throw new ReviewNotFoundException("존재하지 않는 리뷰입니다.");
        if (!owner.getUserNo().equals(userNo)) {
            throw new IllegalStateException("본인만 수정할 수 있습니다.");
        }

        if (files == null || files.size() == 0)
            throw new IllegalArgumentException("이미지는 1장 이상 필수입니다.");

        Review review = new Review();
        review.setReviewNo(reviewNo);
        review.setReviewContent(form.getReviewContent());
        review.setStoreGrade(form.getStoreGrade());
        review.setReservationNo(form.getReservationNo());
        review.setStoreNo(form.getStoreNo());

        reviewMapper.updateReview(review);

        List<String> oldImages = reviewMapper.selectReviewImageUrls(reviewNo);
        for (String url : oldImages) {
            fileService.delete(url);
        }
        reviewMapper.deleteReviewImages(reviewNo);

        for (MultipartFile file : files) {
            String url = fileService.store(file);
            ReviewImage image = new ReviewImage();
            image.setReviewNo(reviewNo);
            image.setImage(url);
            reviewMapper.insertReviewImage(image);
        }
    }

    @Override
    public void deleteReview(Long userNo, Long reviewNo) {
        List<String> urls = reviewMapper.selectReviewImageUrls(reviewNo);
        for (String url : urls) {
            fileService.delete(url);
        }
        reviewMapper.deleteReviewImages(reviewNo);
        reviewMapper.deleteReview(reviewNo);
    }
}
