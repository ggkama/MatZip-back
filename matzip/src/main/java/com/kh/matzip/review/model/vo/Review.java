package com.kh.matzip.review.model.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Review {

    private Long reviewNo;
    private Long reservationNo;
    private Long userNo;
    public String reviewContent;
    public double storeGrade;
    public Date createDate;
    
}
