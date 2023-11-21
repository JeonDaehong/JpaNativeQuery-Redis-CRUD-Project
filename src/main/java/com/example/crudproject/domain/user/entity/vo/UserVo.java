package com.example.crudproject.domain.user.entity.vo;

import com.example.crudproject.domain.user.entity.dto.UserJoinDto;
import com.example.crudproject.domain.user.entity.dto.UserUpdateDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class UserVo {

    private Long userId;
    private String loginId;
    private String userName;
    private String password;
    private LocalDateTime createDateTime;
    private LocalDateTime updateDateTime;

    public static UserVo fromUserJoinDto(UserJoinDto userDto) {
        UserVo userVo = new UserVo();
        userVo.setLoginId(userDto.getLoginId());
        userVo.setUserName(userDto.getUserName());
        userVo.setPassword(userDto.getPassword());
        return userVo;
    }

    public static UserVo fromUserUpdateDto(UserUpdateDto userDto) {
        UserVo userVo = new UserVo();
        userVo.setUserId(userDto.getUserId());
        userVo.setLoginId(userDto.getLoginId());
        userVo.setUserName(userDto.getUserName());
        return userVo;
    }

}
