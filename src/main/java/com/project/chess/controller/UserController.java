package com.project.chess.controller;


import com.project.chess.exception.GlobalExceptionHandler;
import com.project.chess.model.Users;
import com.project.chess.model.dto.ActiveUserDto;
import com.project.chess.model.dto.JwtAuthenticationResponse;
import com.project.chess.model.dto.UsersDto;
import com.project.chess.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/user")
public class UserController extends GlobalExceptionHandler {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/registration")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UsersDto user, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            List<String> fieldErrors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(fieldErrors);
        }

        Users createdUser = userService.createUser(Users.fromUsersDto(user));
        return ResponseEntity.status(HttpStatus.CREATED).body(ActiveUserDto.fromUsers(createdUser));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UsersDto requestedUser) {
        try {
            JwtAuthenticationResponse loggedInUser = userService.login(requestedUser);
            return ResponseEntity.status(HttpStatus.OK)
                    .headers(loggedInUser.getHttpHeaders())
                    .body(loggedInUser.getActiveUserDto());
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-all-users/{username}")
    public ResponseEntity findAllUsersExceptMe(@PathVariable(value = "username") String username) {
        List<ActiveUserDto> displayUsers = userService.getAllUsersExceptMe(username).stream()
                .map(ActiveUserDto::fromUsers).collect(Collectors.toList());
        return new ResponseEntity<>(displayUsers, HttpStatus.OK);
    }

    @GetMapping("/get-all-users")
    public ResponseEntity findAllUsers() {
        List<ActiveUserDto> displayUsers = userService.getAllUsers().stream()
                .map(ActiveUserDto::fromUsers).collect(Collectors.toList());
        return new ResponseEntity<>(displayUsers, HttpStatus.OK);
    }

    @GetMapping("/logout")
    public ResponseEntity logout(Authentication authentication) {
        System.out.println(authentication.getName());
        userService.logout(authentication.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
