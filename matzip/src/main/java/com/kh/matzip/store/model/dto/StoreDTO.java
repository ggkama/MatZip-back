package com.kh.matzip.store.model.dto;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StoreDTO {
    private Long storeNo;
    private Long userNo; 
    private String storeName;
    private String storePhone;
    private String storeAddress1;
    private String storeAddress2;
    private String categoryAddress;
    private String categoryFoodtype;
    private List<String> categoryConvenience;  // 편의시설 여러 개
    private List<String> dayOff;               // 휴무일 여러 개
    private List<String> menuList; //메뉴리스트
    private String openTime;
    private String closeTime;

    private Date createDate;
    private Date modifyDate;
    
    private String isDelete;
    private Long count;
    private String storeImg;
}
