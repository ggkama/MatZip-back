package com.kh.matzip.reservation.model.dto;

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
public class ReservationDTO {
    // 예약 정보
    private Long reservationNo;
    private Long userNo;
    private Long storeNo;
    private String storeName;
    private Date reservationDate;
    private String reservationTime;
    private Long personCount;
    private Date createDate;
    private String status;
    private String storePhone;
    private String storeAddress1;
    private String storeAddress2;
    private String storeImage;
    private String isReview;
    private String userName;
    private String userPhone;
    private String isDeleted;


    // 예약 가능한 조건들 (가게 관련 정보)
    private List<String> dayOff; 
    private String openTime;
    private String closeTime;
    private Date startDate;   // 휴무 시작일
    private Date endDate;     // 휴무 종료일
}
