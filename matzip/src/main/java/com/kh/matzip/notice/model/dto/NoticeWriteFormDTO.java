package com.kh.matzip.notice.model.dto;

import jakarta.validation.constraints.NotBlank;

public class NoticeWriteForm {
    
    public Long noticeNo;
    public Long userNo;

    @NotBlank
    public String noticeTitle;
    @NotBlank
    public String noticeContent;
}
