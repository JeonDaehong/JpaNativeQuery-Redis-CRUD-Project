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

    /**
     * 회원가입
     */
    @Override
    public String joinUser(String loginId, String userName, String password) {

        if ( password == null ) return ResponseCode.ERROR_CODE;

        User overlabCheckUser = userRepository.overlabCheck(loginId);
        if ( overlabCheckUser != null ) return ResponseCode.OVERLAB_ERROR_CODE;

        try {
            String bCryptPassword = bCryptPasswordEncoder.encode(password);

            userRepository.joinUser(
                    loginId,
                    userName,
                    bCryptPassword,
                    LocalDateTime.now(),
                    LocalDateTime.now());

            return ResponseCode.SUCCESS_CODE;

        } catch ( Exception e ) {

            e.printStackTrace();
            return ResponseCode.ERROR_CODE;

        }
    };


    /**
     * 로그인
     */
    @Override
    public User login(String loginId, String password) {
        User dbUser = userRepository.overlabCheck(loginId);
        if ( dbUser != null ) {
            boolean passwordCheck = bCryptPasswordEncoder.matches(password, dbUser.getPassword());
            if (!passwordCheck) dbUser = null;
        }
        return dbUser;
    }


    /**
     * 내 정보 가져오기
     */
    @Override
    public User getMyInfo(Long userId) {
        return userRepository.getMyInfo(userId);
    }


    /**
     * 회원정보 수정
     */
    @Override
    public String updateUser(Long userId, String loginId, String userName) {
        User dbUser = userRepository.overlabCheck(loginId);
        if ( dbUser == null ) return ResponseCode.ERROR_CODE;
        try {
            userRepository.updateUser(userId, userName, LocalDateTime.now());
            return ResponseCode.SUCCESS_CODE;
        } catch ( Exception e ) {
            e.printStackTrace();
            return ResponseCode.ERROR_CODE;
        }
    }

    /**
     * 회원 탈퇴
     */
    @Override
    public String deleteUser(Long userId, String loginId) {
        User dbUser = userRepository.overlabCheck(loginId);
        if ( dbUser == null ) return ResponseCode.ERROR_CODE;
        try {
            userRepository.deleteUser(userId);
            return ResponseCode.SUCCESS_CODE;
        } catch ( Exception e ) {
            e.printStackTrace();
            return ResponseCode.ERROR_CODE;
        }
    }

}
