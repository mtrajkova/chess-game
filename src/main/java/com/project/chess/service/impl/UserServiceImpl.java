package com.project.chess.service.impl;

import com.project.chess.exception.DisplayNameAlreadyExistsException;
import com.project.chess.exception.UserNameAlreadyExistsException;
import com.project.chess.exception.UserNotFoundException;
import com.project.chess.model.Users;
import com.project.chess.model.dto.ActiveUserDto;
import com.project.chess.model.dto.JwtAuthenticationResponse;
import com.project.chess.configuration.security.jwt.JWTProvider;
import com.project.chess.model.dto.UsersDto;
import com.project.chess.repository.UserRepository;
import com.project.chess.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.project.chess.configuration.security.SecurityConstants.HEADER_STRING;
import static com.project.chess.configuration.security.SecurityConstants.TOKEN_PREFIX;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTProvider tokenProvider;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder,
                           AuthenticationManager authenticationManager,
                           JWTProvider tokenProvider) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Users getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public Users createUser(Users newUser) {
        if (userRepository.findByUsername(newUser.getUsername()).isPresent()) {
            throw new UserNameAlreadyExistsException("The user with username: " + newUser.getUsername() + " already exists");
        }
        if (userRepository.findByDisplayName(newUser.getDisplayName()).isPresent()) {
            throw new DisplayNameAlreadyExistsException("The user with display name: " + newUser.getDisplayName() + " already exists");
        }
        newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
        return userRepository.save(newUser);
    }

    @Override
    public Users deleteUser(String username) {
        Users toBeDeleted = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        userRepository.delete(toBeDeleted);
        return toBeDeleted;
    }

    @Override
    public Users getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
    }

    @Override
    public Users updateUserActivityStatus(String username, boolean status) {
        Users forEditing = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        forEditing.setLoggedIn(status);
        userRepository.save(forEditing);
        return forEditing;
    }

    @Override
    public Set<Users> getAllUsersExceptMe(String myUsername) {
        userRepository.findByUsername(myUsername).orElseThrow(() -> new UserNotFoundException(myUsername));
        return userRepository.findAllByUsernameNot(myUsername);
    }

    @Override
    public JwtAuthenticationResponse login(UsersDto requestUser) {
        Authentication authentication = authenticateUser(requestUser);
        Users user = (Users) authentication.getPrincipal();

        updateUserActivityStatus(user.getUsername(), true);

        String jwt = TOKEN_PREFIX.concat(tokenProvider.generateToken(user.getUsername()));
        ActiveUserDto activeUserDto = new ActiveUserDto(user.getUsername(), user.getDisplayName(), user.isLoggedIn());

        return new JwtAuthenticationResponse(activeUserDto, HEADER_STRING, jwt);
    }

    @Override
    public void logout(String username) {
        updateUserActivityStatus(username, false);
        Optional<Users> user = userRepository.findByUsername(username);
        user.ifPresent(users -> SsEmitter.getSseEmitterMap().remove(users.getId()));
    }

    private Authentication authenticateUser(UsersDto requestUser) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(requestUser.getUsername(), requestUser.getPassword());

        Authentication authentication = this.authenticationManager.authenticate(authenticationToken);

        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);

        return authentication;
    }

}
