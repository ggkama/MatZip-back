package com.kh.matzip.mypage.model.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
public class MyProfileDTO {
    private Long userNo;
    private String userId;

    @Size(min=2, max=20, message="이름은 2글자 20글자 이하 입력 가능합니다.")
    private String userName;

    @Size(min=2, max=20, message="닉네임은 2글자 20글자 이하 입력 가능합니다.")
    private String userNickname;

    @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "핸드폰 번호의 형식과 맞지 않습니다. xxx-xxxx-xxxx 또는 xx-xxx-xxxx 형태여야 합니다.")
    private String userPhone;

    private String profileImage;


}
