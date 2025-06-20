package com.kh.matzip.member.model.vo;

import java.sql.Date;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;

@Value
@Getter
@Builder
public class Member {
    private Long userNo;
    private String userId;
    private String userPw;
    private String userName;
    private String userNickName;
    private String userPhone;
    private String userRole;
    private String isDeleted;
}
