package com.kh.matzip.review.model.vo;

import java.sql.Date;

import lombok.Builder;
import lombok.Value;


@Value
@Builder
public class Review {

    private Long reviewNo;
    private Long reservationNo;
    private Long userNo;
    public String reviewContent;
    public double storeGrade;
    public Date createDate;
    
}
