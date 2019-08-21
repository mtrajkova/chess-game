package com.project.chess.controller;


import com.project.chess.model.Users;
import com.project.chess.model.dto.UsersDto;
import com.project.chess.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/registration")
    public ResponseEntity<?> registerUser(@Valid @RequestBody Users user, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            List<String> fieldErrors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(fieldErrors);
        }

        Users createdUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.OK).body(createdUser);
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody UsersDto requestedUser) {
        try {
            UsersDto loggedInUser = userService.login(requestedUser);
            return new ResponseEntity<>(loggedInUser, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
