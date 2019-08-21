package com.project.chess.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class GameControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private ObjectMapper objectMapper;

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

    @Test
    public void getGameById() throws Exception {

        userRepository.save(user1);
        userRepository.save(user2);
        gameRepository.save(game1);

        mockMvc.perform(get("/game/{id}", game1.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(game1.getId()))
                .andExpect(jsonPath("$.playerOne.id").value(game1.getPlayerOne().getId()))
                .andExpect(jsonPath("$.playerTwo.id").value(game1.getPlayerTwo().getId()))
                .andExpect(jsonPath("$.status").value(game1.getStatus().toString()))
                .andExpect(jsonPath("$.playerOneColor").value(game1.getPlayerOneColor().toString()));
    }

    @Test
    public void getAllGamesForUser() throws Exception {

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

        gameRepository.save(game1);
        gameRepository.save(game2);
        gameRepository.save(game3);

        mockMvc.perform(get("/game/user-games/{id}", user2.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(game1.getId()))
                .andExpect(jsonPath("$[0].playerOne.id").value(game1.getPlayerOne().getId()))
                .andExpect(jsonPath("$[0].playerTwo.id").value(game1.getPlayerTwo().getId()))
                .andExpect(jsonPath("$[0].status").value(game1.getStatus().toString()))
                .andExpect(jsonPath("$[0].playerOneColor").value(game1.getPlayerOneColor().toString()))
                .andExpect(jsonPath("$[1].id").value(game3.getId()))
                .andExpect(jsonPath("$[1].playerOne.id").value(game3.getPlayerOne().getId()))
                .andExpect(jsonPath("$[1].playerTwo.id").value(game3.getPlayerTwo().getId()))
                .andExpect(jsonPath("$[1].status").value(game3.getStatus().toString()))
                .andExpect(jsonPath("$[1].playerOneColor").value(game3.getPlayerOneColor().toString()));
    }

//    @Test
//    public void sendRequestForGame() throws Exception {
//
//        userRepository.save(user2);
//        userRepository.save(user3);
//
//        mockMvc.perform(post("/game/new")
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(objectMapper.writeValueAsString(game3))
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isCreated());
//    }

    @Test
    public void updateGameStatus() throws Exception {

        userRepository.save(user1);
        userRepository.save(user2);
        gameRepository.save(game1);

        mockMvc.perform(put("/game/update")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("id", game1.getId().toString())
                .param("status", Status.FINISHED.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}