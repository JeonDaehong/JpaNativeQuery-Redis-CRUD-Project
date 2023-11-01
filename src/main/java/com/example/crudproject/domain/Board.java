package com.example.crudproject.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
@Table(name = "TB_USER_CHAMP_INFO")
public class Board {

    @Id
    @Column(name = "BOARD_SEQ", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardSequence;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "CONTENT", nullable = false)
    private String content;

    @Column(name = "CONTENT_VIEW", nullable = false)
    private int contentView;

    @Column(name = "USER_ID", nullable = false)
    private String userId;

    @Column(name = "USER_NM", nullable = false)
    private String userName;

    @Column(name = "PWSD", nullable = false)
    private String password;

    @Column(name = "CRTE_DTTM", nullable = false)
    private LocalDateTime createDateTime;

    @Column(name = "UPDT_DTTM", nullable = false)
    private LocalDateTime updateDateTime;

    @Builder
    public Board(Long boardSequence, String title, String content, int contentView, String userId, String userName,
                 String password, LocalDateTime createDateTime, LocalDateTime updateDateTime) {
        this.boardSequence = boardSequence;
        this.title = title;
        this.content = content;
        this.contentView = contentView;
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.createDateTime = createDateTime;
        this.updateDateTime = updateDateTime;
    }

}
