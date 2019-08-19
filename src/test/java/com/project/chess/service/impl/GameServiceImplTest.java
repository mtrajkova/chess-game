package com.project.chess.service.impl;

import com.project.chess.model.Game;
import com.project.chess.model.Users;
import com.project.chess.repository.GameRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GameServiceImplTest {

    @Mock
    private GameRepository gameRepository;

    //TODO UserRepositoryMock for checking if players exist

    @InjectMocks
    private GameServiceImpl gameService;

    private Users user2;

    private Game game1;
    private Game game2;
    private Game game3;

    @Before
    public void setUp() {

        Users user1 = new Users()
                .withId(1L)
                .withUsername("nikola");
        user2 = new Users()
                .withId(2L)
                .withUsername("marija");
        Users user3 = new Users()
                .withId(3L)
                .withUsername("slavche");

        game1 = new Game()
                .withId(1L)
                .withPlayerOne(user1)
                .withPlayerTwo(user2)
                .withStatus("active");
        game2 = new Game()
                .withId(2L)
                .withPlayerOne(user1)
                .withPlayerTwo(user3)
                .withStatus("active");
        game3 = new Game()
                .withId(3L)
                .withPlayerOne(user2)
                .withPlayerTwo(user3)
                .withStatus("finished");
    }

    @Test
    public void getGameById() {

        when(gameRepository.findById(game1.getId())).thenReturn(Optional.of(game1));
        Game foundGame = gameService.getGameById(game1.getId());
        assertThat(foundGame, is(equalTo(game1)));
    }

    @Test
    public void getAllGamesByUser() {

        when(gameRepository.findAllByPlayerOneIdOrPlayerTwoId(user2.getId(), user2.getId())).thenReturn(Stream.of(game1, game3).collect(Collectors.toList()));
        List<Game> foundGames = gameService.getAllGamesByUser(user2.getId());
        assertThat(foundGames, is(equalTo(Stream.of(game1, game3).collect(Collectors.toList()))));
    }

    @Test
    public void createGame() {

        when(gameRepository.save(game2)).thenReturn(game2);
        Game createdGame = gameService.createGame(game2);
        assertThat(createdGame, is(equalTo(game2)));
    }

    @Test
    public void updateGameStatus() {

        String oldStatus = game1.getStatus();

        when(gameRepository.findById(game1.getId())).thenReturn(Optional.of(game1));
        when(gameRepository.save(game1)).thenReturn(game1);
        gameService.updateGameStatus("finished", 1L);
        assertThat(game1.getStatus(), is(not(equalTo(oldStatus))));
    }
}