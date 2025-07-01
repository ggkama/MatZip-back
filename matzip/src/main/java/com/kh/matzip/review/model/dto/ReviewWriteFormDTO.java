package com.kh.matzip.review.model.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ReviewWriteFormDTO {
    
    // 작성자는 토큰으로 인증
    private Long reviewNo;
    private Long reservationNo;

    @NotBlank
    private String reviewContent;
    @NotBlank
    private double storeGrade;

    private List<String> imageUrls;
    

}
