package com.project.chess.service.impl;

import com.project.chess.exception.UserAlreadyExistsException;
import com.project.chess.exception.UserNotFoundException;
import com.project.chess.model.Users;
import com.project.chess.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private Users user1 = new Users("lala@gmail.com", "Lala", "11223344", true).withId(1L);
    private Users user2 = new Users("lolo@gmail.com", "Lolo", "556677", true).withId(2L);
    private Users user3 = new Users("lele@gmail.com", "Lele", "889900", false).withId(3L);

    private List<Users> allUsers = Arrays.asList(user1, user2, user3);


    @Test
    public void getAllUsers() {
        Mockito.when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2, user3));
        List<Users> returnedUsers = userService.getAllUsers();

        assertEquals(allUsers, returnedUsers);
    }

    @Test
    public void getUserById() {
        Mockito.when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(new Users(user1)));
        Users returnedUser = userService.getUserById(1L);

        assertEquals(user1, returnedUser);
    }

    @Test
    public void createUserSuccess() {
        Mockito.when(userRepository.findByUsername("viktor@gmail.com")).thenReturn(Optional.empty());
        Mockito.when(userRepository.findByDisplayName("Viktor")).thenReturn(Optional.empty());
        Mockito.when(bCryptPasswordEncoder.encode(Mockito.anyString())).thenReturn("encoded$12");
        Users toBeCreated = new Users("viktor@gmail.com", "Viktor", "LOLOLOLOLO", true);
        Mockito.when(userRepository.save(toBeCreated)).thenReturn(toBeCreated);

        Users returnedUser = userService.createUser(toBeCreated);

        assertEquals(toBeCreated.getUsername(), returnedUser.getUsername());
        assertEquals(toBeCreated.getDisplayName(), returnedUser.getDisplayName());
        assertEquals("encoded$12", returnedUser.getPassword());
    }

    @Test(expected = UserAlreadyExistsException.class)
    public void createUserFailOnDuplicateUsername() {
        Mockito.when(userRepository.findByUsername("lala@gmail.com")).thenReturn(Optional.of(user1));
        Users toBeCreated = new Users("lala@gmail.com", "something new", "LOLOLOLOLO", true);

        userService.createUser(toBeCreated);
    }

    @Test(expected = UserAlreadyExistsException.class)
    public void createUserFailOnDuplicateDisplayName() {
        Mockito.when(userRepository.findByUsername("somethingnew@gmail.com")).thenReturn(Optional.empty());
        Mockito.when(userRepository.findByDisplayName("Lala")).thenReturn(Optional.of(user1));
        Users toBeCreated = new Users("somethingnew@gmail.com", "Lala", "LOLOLOLOLO", true);

        userService.createUser(toBeCreated);
    }

    @Test
    public void deleteUserSuccess() {
        Mockito.when(userRepository.findByUsername("lalala@gmail.com")).thenReturn(Optional.of(user1));

        Users deletedUser = userService.deleteUser("lalala@gmail.com");

        assertEquals(user1, deletedUser);
    }

    @Test(expected = UserNotFoundException.class)
    public void deleteUserFailOnUserNotFound() {
        Mockito.when(userRepository.findByUsername("bbbb@gmail.com")).thenReturn(Optional.empty());

        userService.deleteUser("bbbb@gmail.com");
    }

    @Test
    public void getUserByUsernameSuccess() {
        Mockito.when(userRepository.findByUsername("lala@gmail.com")).thenReturn(Optional.of(user1));

        Users foundUser = userService.getUserByUsername("lala@gmail.com");

        assertEquals(user1, foundUser);
    }

    @Test(expected = UserNotFoundException.class)
    public void getUserByUsernameFailOnUserNotFound() {
        Mockito.when(userRepository.findByUsername("bbbb@gmail.com")).thenReturn(Optional.empty());

        userService.getUserByUsername("bbbb@gmail.com");
    }

    @Test
    public void updateUserActivityStatusSuccess() {
        Mockito.when(userRepository.findByUsername("lala@gmail.com")).thenReturn(Optional.of(new Users(user1)));

        Users updatedUser = userService.updateUserActivityStatus("lala@gmail.com", false);

        assertEquals(user1.getUsername(), updatedUser.getUsername());
        assertEquals(user1.getDisplayName(), updatedUser.getDisplayName());
        assertNotEquals(user1.isLoggedIn(), updatedUser.isLoggedIn());
    }

    @Test(expected = UserNotFoundException.class)
    public void updateUserActivityStatusFailOnUserNotFound() {
        Mockito.when(userRepository.findByUsername("bbbb@gmail.com")).thenReturn(Optional.empty());

        userService.updateUserActivityStatus("bbbb@gmail.com", false);
    }

    @Test
    public void getAllUsersExceptMe() {
        Set<Users> usersWithoutMe = new HashSet<>();
        usersWithoutMe.add(user2);
        usersWithoutMe.add(user3);
        Mockito.when(userRepository.findAllByUsernameNot("lala@gmail.com")).thenReturn(new HashSet<>(usersWithoutMe));

        Set<Users> foundUsers = userService.getAllUsersExceptMe("lala@gmail.com");

        assertEquals(usersWithoutMe, foundUsers);
    }
}