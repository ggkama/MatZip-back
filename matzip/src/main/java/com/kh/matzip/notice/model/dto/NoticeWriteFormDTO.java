package com.kh.matzip.notice.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NoticeWriteFormDTO {
    
    private Long noticeNo;
    private Long userNo;

    @NotBlank
    private String noticeTitle;
    @NotBlank
    private String noticeContent;
}
