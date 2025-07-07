package com.kh.matzip.reservation.model.vo;

import java.util.Date;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Reservation {
    private Long reservationNo;
    private Long userNo;
    private Long storeNo;
    private Date reservationDate;
    private String reservationTime; 
    private Long personCount;
    private Date createDate; 
    private String status; 

}
