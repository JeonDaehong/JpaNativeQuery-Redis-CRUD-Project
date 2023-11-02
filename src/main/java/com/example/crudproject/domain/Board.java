package com.example.crudproject.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
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
    @NotEmpty
    private Long boardSequence;

    @Column(name = "TITLE", nullable = false)
    @NotEmpty
    private String title;

    @Column(name = "CONTENT", nullable = false)
    @NotEmpty
    private String content;

    @Column(name = "CONTENT_VIEW", nullable = false)
    @NotEmpty
    private int contentView;

    @Column(name = "USER_ID", nullable = false)
    @NotEmpty
    private String userId;

    @Column(name = "USER_NM", nullable = false)
    @NotEmpty
    private String userName;

    @Column(name = "PWSD", nullable = false)
    @NotEmpty
    private String password; // 글 수정 시 게시글 password 필요

    @Column(name = "CRTE_DTTM", nullable = false)
    @NotEmpty
    private LocalDateTime createDateTime;

    @Column(name = "UPDT_DTTM", nullable = false)
    @NotEmpty
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
