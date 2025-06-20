package com.kh.matzip.member.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
public class MemberDTO {
    
    private Long userNo;

    @Pattern(regexp="^[a-z0-9+_.-]+@[a-z0-9.-]+$", message="유효한 이메일을 입력해주세요.")
    @NotBlank(message = "이메일(아이디)은 반드시 입력해주세요.")
    private String userId;

    @Pattern(regexp = "^(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,}$", message = "8자 이상, 최소 하나의 특수문자를 포함해서 만들어주세요.")
    @NotBlank(message = "비밀번호는 반드시 입력해주세요.")
    private String userPw;

    private String userName;

    private String userNickName;

    @Pattern(regexp = "^\\d{3}-\\d{4}-\\d{4}$", message = "전화번호는 -를 포함해서 숫자만 입력해주세요.")
    private String userPhone;

    private String userRole;

    private String isDeleted;

}
