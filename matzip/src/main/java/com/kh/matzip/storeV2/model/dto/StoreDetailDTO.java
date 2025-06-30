package com.kh.matzip.storeV2.model.dto;

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
public class StoreDetailDTO {

    private Long storeNo;
    private String storeName;
    private Long storePhone;
    private String storeAddress1;
    private String storeAddress2;
    private String categoryFoodType;
    private String convenience;
    private String openTime;
    private String closeTime;
    private Long count;
    
}
