package com.project.chess.service.impl;

import com.project.chess.exception.UserAlreadyExistsException;
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
//    private final BCryptPasswordEncoder bCryptPasswordEncoder;

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
        if (userRepository.findByUsername(newUser.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException("The user with username " + newUser.getUsername() + " already exists");
        }
//        newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
        return userRepository.save(newUser);
    }

    @Override
    public Users deleteUser(String username) {
        Users toBeDeleted = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("The user with username " + username + " does not exist"));

        userRepository.delete(toBeDeleted);
        return toBeDeleted;
    }

    @Override
    public Users getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("The user with username " + username + " does not exist"));
    }

    @Override
    public Users updateUserActivityStatus(String username, boolean status) {
        Users forEditing = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("The user with username " + username + " does not exist"));

        forEditing.setLoggedIn(status);
        userRepository.save(forEditing);
        return forEditing;
    }

    @Override
    public Set<Users> getAllUsersExceptMe(String myUsername) {
        return userRepository.findAllByUsernameNot(myUsername);
    }
}
