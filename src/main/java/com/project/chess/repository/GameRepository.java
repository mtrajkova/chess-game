package com.project.chess.repository;

import com.project.chess.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long> {

    List<Game> findAllByPlayerOneIdOrPlayerTwoId(Long playerOne_id, Long playerTwo_id);
}
