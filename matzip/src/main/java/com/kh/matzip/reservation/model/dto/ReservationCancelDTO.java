package com.kh.matzip.reservation.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReservationCancelDTO {
    private Long reservationNo;
    private String cancelReason;
}