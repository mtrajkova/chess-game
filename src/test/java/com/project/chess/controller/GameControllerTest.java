package com.project.chess.controller;

import com.project.chess.model.Color;
import com.project.chess.model.Game;
import com.project.chess.model.Status;
import com.project.chess.model.Users;
import com.project.chess.repository.GameRepository;
import com.project.chess.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class GameControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GameRepository gameRepository;

    private MockMvc mockMvc;
    private Users user1;
    private Users user2;
    private Users user3;
    private Game game1;
    private Game game2;
    private Game game3;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        user1 = new Users("ntomikj@endava.com", "ntomikj", "pass123", true);
        user2 = new Users("knastevski@endava.com", "knastevski", "111111", false);
        user3 = new Users("sgjorgjiev@endava.com", "sgjorgjiev", "654321", true);

        game1 = new Game(user1, user2, Status.ACTIVE, new Date(), Color.BLACK);
        game2 = new Game(user1, user3, Status.PENDING, new Date(), Color.WHITE);
        game3 = new Game(user2, user3, Status.FINISHED, new Date(), Color.WHITE);
    }

//    @Test
//    public void getGameById() {
//        userRepository.save(user1);
//        userRepository.save(user2);
//        gameRepository.save(game1);
//
//        mockMvc.perform(get("/game/{id}", game1.getId())
//        .contentType(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath())
//    }

    @Test
    public void getEmitter() {
    }

    @Test
    public void getAllGamesForUser() {
    }

    @Test
    public void sendRequestForGame() {
    }

    @Test
    public void updateGameStatus() {
    }

    @Test
    public void test1() {
    }
}