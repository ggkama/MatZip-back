package com.kh.matzip.notice.model.vo;

import java.sql.Date;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Notice {
    private Long noticeNo;
    private Long userNo;
    private String userName;
    private String noticeTitle;
    private String noticeContent;
    private Date createDate;
}
