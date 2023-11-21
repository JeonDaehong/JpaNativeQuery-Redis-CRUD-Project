package com.example.crudproject.domain.user.service;

import com.example.crudproject.domain.user.entity.dto.UserDeleteDto;
import com.example.crudproject.domain.user.entity.dto.UserJoinDto;
import com.example.crudproject.domain.user.entity.dto.UserLoginDto;
import com.example.crudproject.domain.user.entity.dto.UserUpdateDto;
import com.example.crudproject.domain.user.entity.User;

public interface UserService {

    String joinUser(UserJoinDto userDto);

    User login(UserLoginDto userDto);

    User getMyInfo(Long userId);

    String updateUser(UserUpdateDto userDto);

    String deleteUser(UserDeleteDto userDto);

}
