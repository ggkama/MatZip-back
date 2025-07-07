package com.kh.matzip.admin.manageReview.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.kh.matzip.admin.manageReview.model.dao.ManageReviewMapper;
import com.kh.matzip.admin.manageReview.model.dto.ManageReivewListDTO;
import com.kh.matzip.util.pagenation.PagenationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ManageReviewServiceImpl implements ManageReviewService {

	private final ManageReviewMapper managereviewMapper;
	private final PagenationService pagenationService;
	
	@Override
	public Map<String, Object> getReviewListByAdmin(int page, int size) {

        int startIndex = pagenationService.getStartIndex(page, size);

        Map<String, Object> param = new HashMap<>();
        param.put("startIndex", startIndex);
        param.put("size", size);

        List<ManageReivewListDTO> reviewList = managereviewMapper.selectReviewList(param);
        int totalReivews = managereviewMapper.countAllReview();

        Map<String, Object> result = new HashMap<>();
        result.put("reviewList", reviewList);
        result.put("pageNo", page);
        result.put("size", size);
        result.put("totalReivews", totalReivews);
        result.put("totalPages", (int) Math.ceil((double) totalReivews / size));

        return result;
	}

}
