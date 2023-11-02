package com.example.crudproject;

import com.example.crudproject.domain.Board;
import com.example.crudproject.repository.BoardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.time.LocalDateTime;
import java.util.Optional;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:application.properties")
@DataJpaTest // 자동으로 트랜잭션 롤백을 해줌.
public class JpaMappingTest {

    private final String title = "테스트";
    private final String content = "내용";
    private final int contentView = 0;
    private final String userId = "test";
    private final String userName = "홍길동";
    private final String password = "passwordTest";

    @Autowired
    private BoardRepository boardRepository;


    // JPA의 save를 활용한 테스트 방법
    @BeforeEach
    public void init() {
        boardRepository.save(
                Board.builder()
                .title(title)
                .content(content)
                .contentView(contentView)
                .userId(userId)
                .userName(userName)
                .password(password)
                .createDateTime(LocalDateTime.now())
                .updateDateTime(LocalDateTime.now())
            .build());
    }

    @Test
    public void test() {
        Optional<Board> optionalBoard  = boardRepository.findById(1L);
        if (optionalBoard .isPresent()) {
            Board board = optionalBoard.get();
            assertThat(board.getTitle(), is(title));
            assertThat(board.getContent(), is(content));
            assertThat(board.getContentView(), is(contentView));
            assertThat(board.getUserId(), is(userId));
            assertThat(board.getUserName(), is(userName));
            assertThat(board.getPassword(), is(password));
        } else {
            throw new AssertionError("Board with ID 1 not found");
        }
    }
}
