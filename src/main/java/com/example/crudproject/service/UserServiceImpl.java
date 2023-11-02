package com.example.crudproject.service;

import com.example.crudproject.common.ResponseCode;
import com.example.crudproject.domain.User;
import com.example.crudproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public String joinUser(User user) {

        if ( user.getPassword() == null ) return ResponseCode.ERROR_CODE;

        User overlabCheckUser = userRepository.overlabCheck(user.getLoginId());
        if ( overlabCheckUser != null ) return ResponseCode.OVERLAB_ERROR_CODE;

        try {

            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setCreateDateTime(LocalDateTime.now());
            user.setUpdateDateTime(LocalDateTime.now());

            userRepository.joinUser(user.getLoginId(), user.getLoginId(), user.getPassword(), user.getCreateDateTime(), user.getUpdateDateTime());

            return ResponseCode.SUCCESS_CODE;

        } catch ( Exception e ) {

            e.printStackTrace();
            return ResponseCode.ERROR_CODE;

        }
    };

    @Override
    public User login(String loginId, String password) {

        User dbUser = userRepository.overlabCheck(loginId);

        if ( dbUser != null ) {
            boolean passwordCheck = bCryptPasswordEncoder.matches(password, dbUser.getPassword());
            if (!passwordCheck) dbUser = null;
        }

        return dbUser;

    }

}
