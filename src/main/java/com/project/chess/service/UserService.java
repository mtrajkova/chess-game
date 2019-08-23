package com.project.chess.service;

import com.project.chess.model.Users;
import com.project.chess.model.dto.JwtAuthenticationResponse;
import com.project.chess.model.dto.UsersDto;

import java.util.List;
import java.util.Set;

public interface UserService {
    List<Users> getAllUsers();

    Users getUserById(Long id);

    Users createUser(Users newUser);

    Users deleteUser(String username);

    Users getUserByUsername(String username);

    Set<Users> getAllUsersExceptMe(String myUsername);

    JwtAuthenticationResponse login(UsersDto requestUser);

    void logout(String username);
}
