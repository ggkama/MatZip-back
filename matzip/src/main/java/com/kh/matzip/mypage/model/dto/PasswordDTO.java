package com.kh.matzip.mypage.model.dto;

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
public class PasswordDTO {
    @Pattern(
        regexp = "^(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,}$",
        message = "8자 이상, 최소 하나의 특수문자를 포함해서 만들어주세요."
    )
    @NotBlank(message = "현재 비밀번호는 반드시 입력해주세요.")
    private String currentPw;

    @Pattern(
        regexp = "^(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,}$",
        message = "8자 이상, 최소 하나의 특수문자를 포함해서 만들어주세요."
    )
    @NotBlank(message = "새 비밀번호는 반드시 입력해주세요.")
    private String newPw;
}
