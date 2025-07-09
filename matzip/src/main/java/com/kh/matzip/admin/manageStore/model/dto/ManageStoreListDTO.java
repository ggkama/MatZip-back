package com.kh.matzip.admin.manageStore.model.dto;

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
public class ManageStoreListDTO {
	
	private Long storeNo;
	private String storeName;
	private String categoryAddress;
	private Double storeGrade;
	private Date createDate;
	
}
