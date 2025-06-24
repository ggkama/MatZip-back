package com.kh.matzip.notice.model.service;

import java.util.Map;

import com.kh.matzip.notice.model.dto.NoticeDTO;
import com.kh.matzip.notice.model.dto.NoticeWriteFormDTO;


public interface NoticeService {

    Map<String, Object> selectNoticeList(int pageNo, int size);

    NoticeDTO selectNoticeDetail(Long noticeNo);

    void insertNotice(NoticeWriteFormDTO form);
    
    void updateNotice(Long noticeNo, NoticeWriteFormDTO form);
    
    void deleteNotice(Long noticeNo);

    

}