package com.example.crudproject.repository;

import com.example.crudproject.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    // JPA가 아닌 직접 SQL 쿼리문 작성을 공부
    // JPA의 직접 쿼리문 작성에는 우리가 흔히 아는 SQL과, JPA를 반 정도 사용해서 쿼리문을 작성하는 JPQL이 있다.
    // @Query 이후 nativeQuery 가 true이면 SQL / nativeQuery가 false이면 JPQL 이다.
    // 그리고 @Query 를 사용한 insert, update, delete 시에는 @Modifying을 붙여주는게 좋다.
    // @Modifying 어노테이션을 사용하면 Spring Data JPA가 해당 메서드가 데이터베이스에 변경을 가하고, 트랜잭션 관리와 관련된 일부 처리를 수행하도록 지시한다.
    @Modifying( clearAutomatically = true )
    @Query( value = "INSERT INTO TB_BOARD (TITLE, CONTENT, CONTENT_VIEW, USER_ID, USER_NM, PWSD, CRTE_DTTM, UPDT_DTTM)" +
                    "VALUES (:title, :content, :contentView, :userId, :userName, :password, :createDateTime, :updateDateTime)",
            nativeQuery = true )
    void insertPost(@Param("board") Board board);

}
