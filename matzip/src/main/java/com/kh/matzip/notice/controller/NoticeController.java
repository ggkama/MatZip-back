package com.kh.matzip.notice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.matzip.notice.model.dto.NoticeWriteFormDTO;
import com.kh.matzip.notice.model.service.NoticeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notice")
public class NoticeController {
    
    private final NoticeService noticeService;


    // 공지사항 리스트조회
    @GetMapping
    public ResponseEntity<?> getNoticeList(
        @RequestParam(name = "page", defaultValue="0") int page,
        @RequestParam(name = "size") int size) {

        return ResponseEntity.ok(noticeService.selectNoticeList(page,size));
        }

    // 공지사항 상세조회
    @GetMapping("/{noticeNo}")
    public ResponseEntity<?> getNoticeDetail(@PathVariable Long noticeNo) {
        return ResponseEntity.ok(noticeService.selectNoticeDetail(noticeNo));
        }   

    // 공지사항 작성
    // writeForm 사용
    @PostMapping
    public ResponseEntity<?> insertNotice(
        @RequestBody @Valid NoticeWriteFormDTO form) {
        noticeService.insertNotice(form);
        return ResponseEntity.status(HttpStatus.CREATED).body("공지사항이 등록되었습니다.");
    }

    // 공지사항 수정
    @PutMapping
    public ResponseEntity<?> updateNotice(
           @PathVariable Long noticeNo,
           @RequestBody @Valid NoticeWriteFormDTO form){

            noticeService.updateNotice(noticeNo, form);
            return ResponseEntity.ok("공지사항이 수정되었습니다.");
           }


}
