package com.kh.matzip.review.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReviewWriteFormDTO {
    
    // 작성자는 토큰으로 인증
    public Long reviewNo;
    public Long reservationNo;

    @NotBlank
    public String reviewContent;
    @NotBlank
    public double storeGrade;


}
