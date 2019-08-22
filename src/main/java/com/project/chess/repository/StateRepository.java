package com.project.chess.repository;

import com.project.chess.model.State;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateRepository extends JpaRepository<State,String> {
}
