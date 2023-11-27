package com.example.crudproject.domain.board.service;

import com.example.crudproject.domain.board.entity.dto.ScoreDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ScoreService {

    String registerScore(ScoreDto scoreDto);

    String updateScore(ScoreDto scoreDto);

    void calculateRating();

}
