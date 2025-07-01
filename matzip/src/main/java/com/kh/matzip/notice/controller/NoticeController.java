package com.kh.matzip.notice.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.matzip.member.model.vo.CustomUserDetails;
import com.kh.matzip.notice.model.dto.NoticeDTO;
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
    public ResponseEntity<Map<String, Object>> getNoticeList(
        @RequestParam(name = "page", defaultValue="0") int page,
        @RequestParam(name = "size") int size) {

        return ResponseEntity.ok(noticeService.selectNoticeList(page,size));
        }

    // 공지사항 상세조회
    @GetMapping("/{noticeNo}")
    public ResponseEntity<List<NoticeDTO>> getNoticeDetail(@PathVariable Long noticeNo) {
        return ResponseEntity.ok(noticeService.selectNoticeDetail(noticeNo));
        }   

    // 공지사항 작성
    // writeForm 사용
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/write")
    public ResponseEntity<String> insertNotice(
        @AuthenticationPrincipal CustomUserDetails user,
        @RequestBody @Valid NoticeWriteFormDTO form) {

        form.setUserNo(user.getUserNo());
        noticeService.insertNotice(form);
        return ResponseEntity.status(HttpStatus.CREATED).body("공지사항이 등록되었습니다.");
    }

    // 공지사항 수정
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    public ResponseEntity<String> updateNotice(
           @PathVariable Long noticeNo,
           @AuthenticationPrincipal CustomUserDetails user,
           @RequestBody @Valid NoticeWriteFormDTO form){

            form.setUserNo(user.getUserNo());
            noticeService.updateNotice(noticeNo, form);
            return ResponseEntity.ok("공지사항이 수정되었습니다.");
           }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{noticeNo}")
    public ResponseEntity<String> deleteNotice(
        @PathVariable Long noticeNo) {
        noticeService.deleteNotice(noticeNo);
        return ResponseEntity.ok("공지사항이 삭제되었습니다.");
        }


}