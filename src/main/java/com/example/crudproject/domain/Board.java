package com.example.crudproject.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@ToString
@NoArgsConstructor
@Table(name = "TB_BOARD")
public class Board {

    @Id
    @Column(name = "BOARD_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "CONTENT", nullable = false)
    private String content;

    @Column(name = "BOARD_VIEW", nullable = false)
    private int boardView;

    @Column(name = "CRTE_DTTM", nullable = false)
    private LocalDateTime createDateTime;

    @Column(name = "UPDT_DTTM", nullable = false)
    private LocalDateTime updateDateTime;

    @Column(name = "USER_ID", nullable = false)
    private Long userId;

    @Builder
    public Board(Long boardId, String title, String content, int boardView, LocalDateTime createDateTime, LocalDateTime updateDateTime, Long userId) {
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.boardView = boardView;
        this.createDateTime = createDateTime;
        this.updateDateTime = updateDateTime;
        this.userId = userId;
    }

}
