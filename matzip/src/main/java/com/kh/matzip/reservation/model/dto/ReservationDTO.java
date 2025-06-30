package com.kh.matzip.reservation.model.dto;

import java.util.Date;

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
    private Long reservationNo;
    private Long userNo;
    private Long storeNo;
    private Date reservationDate;
    private String reservationTime;
    private int  personCount;
    private Date createDate;

}
