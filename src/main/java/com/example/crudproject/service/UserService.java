package com.example.crudproject.service;

import com.example.crudproject.domain.User;

public interface UserService {

    String joinUser(User user);

    User login(String loginId, String password);

    String updateUser(User user);

    String deleteUser(String loginId);

}
