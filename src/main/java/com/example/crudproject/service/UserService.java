package com.example.crudproject.service;

import com.example.crudproject.domain.User;

public interface UserService {

    String joinUser(String loginId, String userName, String password);

    User login(String loginId, String password);

    User getMyInfo(Long userId);

    String updateUser(Long userId, String loginId, String userName);

    String deleteUser(Long userId, String loginId);

}
