package com.kh.matzip.store.vo;

import java.util.Date;
import java.util.List;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Store {
    private Long storeNo;
    private Long userNo;
    private String storeName;
    private String storePhone;
    private String storeAddress1;
    private String storeAddress2;
    private String categoryAddress;
    private String categoryFoodtype;
    private List<String> categoryConvenience;
    private List<String> dayOff;
    private List<String> menuList;
    private List<String> imageList;
    private String openTime;
    private String closeTime;
    private Date startDate;
    private Date endDate;
    private Date createDate;
    private Date modifyDate;
    private String isDelete;
    private Long count;
    private String storeImg;
}
