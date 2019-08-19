package com.project.chess.service.impl;

import com.project.chess.repository.GameRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class GameServiceImplTest {

    @Mock
    private GameRepository gameRepository;

    //TODO UserRepositoryMock for checking if players exist

    @InjectMocks
    private GameServiceImpl gameService;

    @Test
    public void getGameById() {

    }

    @Test
    public void getAllGamesByUser() {
    }

    @Test
    public void createGame() {
    }

    @Test
    public void updateGameStatus() {
    }
}