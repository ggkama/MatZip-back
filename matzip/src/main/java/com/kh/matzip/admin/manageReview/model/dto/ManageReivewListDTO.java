package com.kh.matzip.admin.manageReview.model.dto;

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
public class ManageReivewListDTO {

	private Long reviewNo;
	private String storeName;
	private String userNickname;
	private Double storeGrade;
	private Date reviewDate;
	
}
