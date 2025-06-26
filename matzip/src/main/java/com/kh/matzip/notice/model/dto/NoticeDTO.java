package com.kh.matzip.notice.model.dto;

import java.sql.Date;

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
public class NoticeDTO {
    
    private Long noticeNo;
    private Long userNo;
    @NotBlank
    private String noticeTitle;
    @NotBlank
    private String noticeContent;
    private Date createDate;
    
}
