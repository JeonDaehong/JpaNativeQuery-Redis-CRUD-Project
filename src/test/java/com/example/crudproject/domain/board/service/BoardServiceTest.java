package com.example.crudproject.domain.board.service;

import com.example.crudproject.domain.board.entity.Board;
import com.example.crudproject.domain.board.repository.BoardRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class BoardServiceTest {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    @DisplayName("동시에 여러 스레드에서 게시글 view 증가에 접근한다면?")
    void boardViewTest() throws InterruptedException {

        final int THREAD_COUNT = 10000;

        Board board = Board.builder()
                .title("테스트")
                .content("테스트")
                .boardView(0) // assuming you want to initialize boardView to 0
                .createDateTime(LocalDateTime.now())
                .updateDateTime(LocalDateTime.now())
                .userId((long)1)
                .build();

        Long boardId = boardRepository.save(board).getBoardId();
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        CountDownLatch countDownLatch = new CountDownLatch(THREAD_COUNT);

        for (int i=0; i<THREAD_COUNT; i++) {
            executorService.submit(() -> {
               try {
                   boardRepository.addBoardView(boardId);
               } finally {
                   countDownLatch.countDown();
               }
            });
        }

        countDownLatch.await();

        Optional<Board> actualOptional = boardRepository.findById(boardId);
        if (actualOptional.isPresent()) {
            Board actual = actualOptional.get();
            System.out.println("투 스트링? : " + actual.toString());
            assertEquals(THREAD_COUNT, actual.getBoardView(), "The boardView should be equal to 10000");
        } else {
            // Handle the case where the Optional is empty if needed
            System.out.println("Board not found with ID: " + boardId);
        }
    }
}
