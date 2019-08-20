package com.project.chess.repository;

import com.project.chess.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Set<Users> findAllByIdNot(Long id);
}
