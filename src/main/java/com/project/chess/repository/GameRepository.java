package com.project.chess.repository;

import com.project.chess.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GameRepository extends JpaRepository<Game, Long> {
    List<Game> findAll();

    Optional<Game> findById(Long id);

    Game save(Game game);
}
