package com.kh.matzip.notice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping
    public ResponseEntity<?> getNoticeList(
        @RequestParam(name = "page", defaultValue="0") int page,
        @RequestParam(name = "size") int size) {

        return ResponseEntity.ok(noticeService.findNoticeList(page,size));
        }

    @GetMapping({noticeId})
    public ResponseEntity<?> getNoticeDetail(
        @RequestParam()
    )

}
