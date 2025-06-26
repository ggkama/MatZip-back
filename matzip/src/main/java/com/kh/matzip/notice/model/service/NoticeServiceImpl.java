package com.kh.matzip.notice.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.kh.matzip.global.enums.ResponseCode;
import com.kh.matzip.global.error.exceptions.InvalidFormatException;
import com.kh.matzip.global.error.exceptions.NoticeNotFoundException;
import com.kh.matzip.notice.model.dao.NoticeMapper;
import com.kh.matzip.notice.model.dto.NoticeDTO;
import com.kh.matzip.notice.model.dto.NoticeWriteFormDTO;
import com.kh.matzip.notice.model.vo.Notice;
import com.kh.util.pagenation.PagenationService;

import jakarta.transaction.Transactional;
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


    @Override
    public Map<String, Object> selectNoticeList(int pageNo, int size) {
        if (pageNo < 0 || size < 1) {
            throw new InvalidFormatException(ResponseCode.BAD_REQUEST, "pageNo 또는 size 값이 올바르지 않습니다.");
        }

        int startIndex = pagenation.getStartIndex(pageNo, size);

        Map<String, String> pageInfo = new HashMap<>();
        pageInfo.put("startIndex", String.valueOf(startIndex));
        pageInfo.put("size", String.valueOf(size));

        List<NoticeDTO> noticeList = noticeMapper.selectNoticeList(pageInfo);
        int totalCount = noticeMapper.selectNoticeCount(pageInfo);

        Map<String, Object> result = new HashMap<>();
        result.put("noticeList", noticeList);
        result.put("totalCount", totalCount);

        return result;
    }

    @Override
    public List<NoticeDTO> selectNoticeDetail(Long noticeNo) {
        if (noticeNo == null || noticeNo < 1) {
            throw new InvalidFormatException(ResponseCode.BAD_REQUEST, "공지사항 ID가 유효하지 않습니다.");
        }

        List<NoticeDTO> notice = noticeMapper.selectNoticeDetail(noticeNo);
        if (notice == null) {
            throw new NoticeNotFoundException(ResponseCode.NOT_FOUND, "해당 공지사항이 존재하지 않습니다.");
        }

        return notice;
    }

    @Transactional
    @Override
    public void insertNotice(NoticeWriteFormDTO form) {
        Notice notice = Notice.builder()
                .noticeNo(form.getNoticeNo())
                .noticeTitle(form.getNoticeTitle())
                .noticeContent(form.getNoticeContent())
                .build();

        noticeMapper.insertNotice(notice);
        log.info("공지사항 등록 완료");
    }

    @Transactional
    @Override
    public void updateNotice(Long noticeNo, NoticeWriteFormDTO form) {
        if (noticeNo == null || noticeNo < 1) {
            throw new InvalidFormatException(ResponseCode.BAD_REQUEST, "공지사항 ID가 유효하지 않습니다.");
        }

        Notice notice = Notice.builder()
                .noticeNo(form.getNoticeNo())
                .noticeTitle(form.getNoticeTitle())
                .noticeContent(form.getNoticeContent())
                .build();

        int updated = noticeMapper.updateNotice(notice);
        if (updated == 0) {
            throw new NoticeNotFoundException(ResponseCode.NOT_FOUND, "수정 대상 공지사항이 존재하지 않습니다.");
        }

        log.info("공지사항 수정 완료", noticeNo);
    }

    @Transactional
    @Override
    public void deleteNotice(Long noticeNo) {
        if (noticeNo == null || noticeNo < 1) {
            throw new InvalidFormatException(ResponseCode.BAD_REQUEST, "공지사항 ID가 유효하지 않습니다.");
        }

        int deleted = noticeMapper.deleteNotice(noticeNo);
        if (deleted == 0) {
            throw new NoticeNotFoundException(ResponseCode.NOT_FOUND, "삭제 대상 공지사항이 존재하지 않습니다.");
        }

        log.info("공지사항 삭제 완료", noticeNo);
    }
}

