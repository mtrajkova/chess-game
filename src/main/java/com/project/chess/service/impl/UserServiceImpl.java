package com.project.chess.service.impl;

import com.project.chess.exception.UserNotFoundException;
import com.project.chess.model.Users;
import com.project.chess.repository.UserRepository;
import com.project.chess.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Users getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("The user with id " + id + " does not exist"));
    }

    @Override
    public Users createUser(Users newUser) {
        return null;
    }

    @Override
    public Users deleteUser(Long id) {
        return null;
    }

    @Override
    public Users updateUserActivityStatus(Long id, boolean status) {
        return null;
    }

    @Override
    public Set<Users> getAllUsersExceptMe(Long myId) {
        return null;
    }
}
