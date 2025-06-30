package com.kh.matzip.notice.model.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.kh.matzip.notice.model.dto.NoticeDTO;
import com.kh.matzip.notice.model.vo.Notice;

@Mapper
public interface NoticeMapper {

    // 조회
    List<NoticeDTO> selectNoticeList(Map<String, String> pageInfo);
    
    // 상세 조회
    List<NoticeDTO> selectNoticeDetail(Long noticeNo);

    // 글작성
    int insertNotice(Notice notice);

    // 글수정
    int updateNotice(Notice notice);

    // 글삭제
    int deleteNotice(Long noticeNo);

    // 글 전체 개수조회
    int selectNoticeCount(Map<String, String> pageInfo);
    
}