package com.example.crudproject.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@ToString
@NoArgsConstructor
@Table(name = "TB_USER")
public class User {

    @Id
    @Column(name = "USER_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "LOGIN_ID", nullable = false)
    private String loginId;

    @Column(name = "USER_NM", nullable = false)
    private String userName;

    @Column(name = "PWSD", nullable = false)
    private String password;

    @Column(name = "CRTE_DTTM", nullable = false)
    private LocalDateTime createDateTime;

    @Column(name = "UPDT_DTTM", nullable = false)
    private LocalDateTime updateDateTime;

    // 필요에 의해 만든 Setter
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Builder
    public User(Long userId, String loginId, String userName,
                String password, LocalDateTime createDateTime, LocalDateTime updateDateTime) {
        this.userId = userId;
        this.loginId = loginId;
        this.userName = userName;
        this.password = password;
        this.createDateTime = createDateTime;
        this.updateDateTime = updateDateTime;
    }

}
