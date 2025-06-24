package com.kh.matzip.notice.model.service;

import java.util.List;
import java.util.Map;

import com.kh.matzip.notice.model.dto.NoticeDTO;
import com.kh.matzip.notice.model.dto.NoticeWriteFormDTO;


public interface NoticeService {

    Map<String, Object> selectNoticeList(int pageNo, int size);

    List<NoticeDTO> selectNoticeDetail(Long noticeNo);

    void insertNotice(NoticeWriteFormDTO form);
    
    void updateNotice(Long noticeNo, NoticeWriteFormDTO form);
    
    void deleteNotice(Long noticeNo);

    

}