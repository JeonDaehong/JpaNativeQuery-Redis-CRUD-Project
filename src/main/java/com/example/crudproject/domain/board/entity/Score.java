package com.example.crudproject.domain.board.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Entity
@ToString
@NoArgsConstructor
@Table(name = "TB_BOARD_SCORE")
public class Score { // 복합키일 경우 Serializable 구현해야함.

    @Id
    @Column(name = "BOARD_ID", nullable = false)
    private Long boardId;

    @Column(name = "USER_ID", nullable = false)
    private Long userId;

    @Column(name = "SCORE", nullable = false)
    private int score;

    @Column(name = "CRTE_DTTM", nullable = false)
    private LocalDateTime createDateTime;

    @Column(name = "UPDT_DTTM", nullable = false)
    private LocalDateTime updateDateTime;
}
