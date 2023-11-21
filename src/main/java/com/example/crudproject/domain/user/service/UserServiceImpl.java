package com.example.crudproject.domain.user.service;

import com.example.crudproject.common.code.ResponseCode;
import com.example.crudproject.domain.board.repository.BoardRepository;
import com.example.crudproject.domain.board.repository.ScoreRepository;
import com.example.crudproject.domain.user.entity.dto.UserDeleteDto;
import com.example.crudproject.domain.user.entity.dto.UserJoinDto;
import com.example.crudproject.domain.user.entity.dto.UserLoginDto;
import com.example.crudproject.domain.user.entity.dto.UserUpdateDto;
import com.example.crudproject.domain.user.entity.User;
import com.example.crudproject.domain.user.entity.vo.UserVo;
import com.example.crudproject.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final ScoreRepository scoreRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 회원가입
     */
    @Transactional
    @Override
    public String joinUser(UserJoinDto userDto) {

        User overlabCheckUser = userRepository.overlabCheck(userDto.getLoginId());
        if ( overlabCheckUser != null ) return ResponseCode.OVERLAB_ERROR_CODE;

        try {
            UserVo userVo = UserVo.fromUserJoinDto(userDto);
            userVo.setPassword(bCryptPasswordEncoder.encode(userVo.getPassword()));
            userVo.setCreateDateTime(LocalDateTime.now());
            userVo.setUpdateDateTime(LocalDateTime.now());

            userRepository.joinUser(
                    userVo.getLoginId(),
                    userVo.getUserName(),
                    userVo.getPassword(),
                    userVo.getCreateDateTime(),
                    userVo.getUpdateDateTime());

            return ResponseCode.SUCCESS_CODE;

        } catch ( Exception e ) {
            e.printStackTrace();
            log.error(e.getMessage());
            return ResponseCode.ERROR_CODE;
        }
    };

    /**
     * 로그인
     */
    @Override
    public User login(UserLoginDto userDto) {
        User dbUser = userRepository.overlabCheck(userDto.getLoginId());
        if ( dbUser != null ) {
            boolean passwordCheck = bCryptPasswordEncoder.matches(userDto.getPassword(), dbUser.getPassword());
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
     * 유저 정보 업데이트
     */
    @Transactional
    @Override
    public String updateUser(UserUpdateDto userDto) {
        User dbUser = userRepository.overlabCheck(userDto.getLoginId());
        if ( dbUser == null ) return ResponseCode.ERROR_CODE;
        try {
            UserVo userVo = UserVo.fromUserUpdateDto(userDto);
            userVo.setUpdateDateTime(LocalDateTime.now());
            userRepository.updateUser(userVo.getUserId(), userVo.getUserName(), userVo.getUpdateDateTime());
            return ResponseCode.SUCCESS_CODE;
        } catch ( Exception e ) {
            e.printStackTrace();
            log.error(e.getMessage());
            return ResponseCode.ERROR_CODE;
        }
    }


    /**
     * 회원 탈퇴
     */
    @Transactional
    @Override
    public String deleteUser(UserDeleteDto userDto) {
        try {
            scoreRepository.deleteScoreByUser(userDto.getUserId()); // 해당 유저의 모든 평점 삭제 (외래키 연결)
            boardRepository.deleteBoardAllByUser(userDto.getUserId()); // 해당 유저의 모든 게시글 삭제 (외래키 연결)
            userRepository.deleteUser(userDto.getUserId()); // 회원 탈퇴
            return ResponseCode.SUCCESS_CODE;
        } catch ( Exception e ) {
            e.printStackTrace();
            log.error(e.getMessage());
            return ResponseCode.ERROR_CODE;
        }
    }

}
