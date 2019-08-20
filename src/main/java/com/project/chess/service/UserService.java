package com.project.chess.service;

import com.project.chess.model.Users;

import java.util.List;
import java.util.Set;

public interface UserService {
    List<Users> getAllUsers();

    Users getUserById(Long id);

    Users createUser(Users newUser);

    Users deleteUser(Long id);

    Users updateUserActivityStatus(Long id, boolean status);

    Set<Users> getAllUsersExceptMe(Long myId);
}
