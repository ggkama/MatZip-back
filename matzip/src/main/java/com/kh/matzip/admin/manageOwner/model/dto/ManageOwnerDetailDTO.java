package com.kh.matzip.admin.manageOwner.model.dto;

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
public class ManageOwnerDetailDTO {

	private Long registerNo;
	private String businessNo;
	private String storeName;
	private String image;
	private Date requestDate;
	private String status;
	
}
