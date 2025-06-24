package com.kh.matzip.notice.model.service;

import org.springframework.stereotype.Service;

import com.kh.matzip.notice.model.dao.NoticeMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {

    private final NoticeMapper noticeMapper;
    //private final authService authService;
    
}
