package com.kh.matzip.notice.model.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.kh.matzip.notice.model.dto.NoticeDTO;
import com.kh.matzip.notice.vo.Notice;

@Mapper
public interface NoticeMapper {

    // 조회
    List<NoticeDTO> selectNoticeList(Map<String, String> pageInfo);
    
    // 상세 조회
    List<NoticeDTO> selectNoticeDetail(Long noticeNo);

    // 글작성
    void insertNotice(Notice notice);
    Long selectAuthBoardNo(Long userNo);

    // 글수정
    void updateNotice(Notice notice);

    // 글수정
    void deleteNotice(Long notice_no);
    
}
