package com.kh.matzip.store.vo;

import java.util.Date;
import java.util.List;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Store {
    private String storePhone;
    private String storeAddress1;
    private String storeAddress2;
    private String categoryAddress;
    private String categoryFoodtype;
    private List<String> categoryConvenience;  // 편의시설 여러 개
    private List<String> dayOff;               // 휴무일 여러 개
    private String openTime;
    private String closeTime;
    private Date createDate;
    private Date modifyDate;
    private String isDelete;
    private Long count;
    private String storeImg;
}
