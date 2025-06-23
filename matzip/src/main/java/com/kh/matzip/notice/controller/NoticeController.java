package com.kh.matzip.notice.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.matzip.notice.model.service.NoticeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notice")
public class NoticeController {
    
    private final NoticeService noticeService;

    @Getmapping
    publice ResponseEntity<?> selectNoticeList

}
