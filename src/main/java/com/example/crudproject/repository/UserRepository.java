package com.example.crudproject.repository;

import com.example.crudproject.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface UserRepository extends JpaRepository<User, Long> {

    @Modifying
    @Query( value = "INSERT INTO TB_USER (LOGIN_ID, USER_NM, PWSD, CRTE_DTTM, UPDT_DTTM)" +
                    "VALUES (:loginId, :userName, :password, :createDateTime, :updateDatetime)",
            nativeQuery = true)
    void joinUser(@Param(value = "loginId") String loginId,
                  @Param(value = "userName") String userName,
                  @Param(value = "password") String password,
                  @Param(value = "createDateTime") LocalDateTime createDateTime,
                  @Param(value = "updateDatetime") LocalDateTime updateDatetime);


    @Query ( value = "SELECT * FROM TB_USER WHERE LOGIN_ID = :loginId", nativeQuery = true )
    User overlabCheck(@Param(value = "loginId") String loginId);
}
