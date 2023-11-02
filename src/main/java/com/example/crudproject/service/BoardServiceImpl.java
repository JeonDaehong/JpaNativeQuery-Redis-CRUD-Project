package com.example.crudproject.service;

import com.example.crudproject.domain.Board;
import com.example.crudproject.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor // 생성자 생략 ( 대신 final로 선언해야 함 )
@Slf4j
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public void insertPost(Board board) {
        if (board.getPassword() == null) {
            throw new IllegalArgumentException("Password cannot be null");
        }
        board.setPassword(passwordEncoder.encode(board.getPassword()));
        boardRepository.insertPost(board);
    }

}
