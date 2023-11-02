package com.example.crudproject.service;

import com.example.crudproject.domain.Board;

public interface BoardService {

    // 인터페이스 내의 모든 메서드는 기본적으로 public 접근 제어자를 가지며, 따라서 public을 명시적으로 추가하는 것은 불필요하다.
    void insertPost(Board board);

}
