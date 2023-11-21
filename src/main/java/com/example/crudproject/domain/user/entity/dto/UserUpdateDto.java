package com.example.crudproject.domain.user.entity.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
public class UserUpdateDto {

    @NotNull(message = "유저 아이디는 반드시 있어야합니다.")
    private Long userId;

    @NotNull(message = "아이디는 최소 한 글자에서 최대 40자 이하여야 합니다.")
    @Size(min = 1, max = 40)
    private String loginId;

    @NotNull(message = "이름은 최소 한 글자에서 최대 40자 이하여야 합니다.")
    @Size(min = 1, max = 40)
    private String userName;

}
