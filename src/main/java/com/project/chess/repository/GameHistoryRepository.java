package com.project.chess.repository;

import com.project.chess.model.GameHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameHistoryRepository extends JpaRepository<GameHistory, Long> {
}
