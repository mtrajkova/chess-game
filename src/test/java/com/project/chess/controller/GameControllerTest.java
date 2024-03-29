package com.project.chess.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.chess.model.*;
import com.project.chess.repository.GameRepository;
import com.project.chess.repository.UserRepository;
import com.project.chess.service.impl.SsEmitter;
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
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class GameControllerTest {

    private static final String URL_GAME = "/games/{id}";
    private static final String URL_GAMES = "/games";

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
    private Game game4;
    private State state;

    @Before
    public void setUp() {

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        state = new State("Blablablalba");

        user1 = new Users("ntomikj@endava.com", "ntomikj", "pass123", true);
        user2 = new Users("knastevski@endava.com", "knastevski", "111111", false);
        user3 = new Users("sgjorgjiev@endava.com", "sgjorgjiev", "654321", true);

        game1 = new Game(user1, user2, Status.ACTIVE, new Date(), Color.BLACK, new State(state));
        game2 = new Game(user1, user3, Status.PENDING, new Date(), Color.WHITE, new State(state));
        game3 = new Game(user2, user3, Status.FINISHED, new Date(), Color.WHITE, new State(state));
        game4 = new Game(user2, user3, Status.FINISHED, new Date(), Color.WHITE, new State(state));
    }

    @Test
    public void getGameById() throws Exception {

        userRepository.save(user1);
        userRepository.save(user2);
        gameRepository.save(game1);

        mockMvc.perform(get(URL_GAME, game1.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(game1.getId()))
                .andExpect(jsonPath("$.playerOne.id").value(game1.getPlayerOne().getId()))
                .andExpect(jsonPath("$.playerTwo.id").value(game1.getPlayerTwo().getId()))
                .andExpect(jsonPath("$.status").value(game1.getStatus().toString()))
                .andExpect(jsonPath("$.playerOneColor").value(game1.getPlayerOneColor().toString()));
    }



    @Test
    public void sendRequestForGame() throws Exception {

        userRepository.save(user2);
        userRepository.save(user3);

        SsEmitter.getSseEmitterMap().put(game4.getPlayerTwo().getId(), new SseEmitter());

        mockMvc.perform(post(URL_GAMES)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(game4))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void updateGameStatus() throws Exception {

        userRepository.save(user1);
        userRepository.save(user2);
        gameRepository.save(game1);

        mockMvc.perform(put(URL_GAME, game1.getId().toString())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("status", Status.FINISHED.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
