package com.kh.matzip.notice.model.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.kh.matzip.notice.model.dao.NoticeMapper;
import com.kh.util.pagenation.PagenationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {

    private final NoticeMapper noticeMapper;
    //private final authService authService;
    private final PagenationService pagenation;
    private final NoticeService noticeService;


    // 공지사항 조회
    @Override
    public Map<String, Object> selectNoticeList(int pageNo, int size){
        int startIndex = pagenation.getStartIndex(pageNo, size);
        Map<String, String> pageInfo = new HashMap<>();
        pageInfo.put("startIndex", Integer.toString(startIndex));
        pageInfo.put("size", Integer.toString(size));
        
        Map<String, Object> resultData = new HashMap<String,Object>();

        return resultData;
    }

    
    
}
