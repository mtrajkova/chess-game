package com.project.chess.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.project.chess.model.*;
import com.project.chess.model.dto.ActiveUserDto;
import com.project.chess.model.dto.UsersDto;
import com.project.chess.repository.GameRepository;
import com.project.chess.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

    private static final String URL_USER_REGISTRATION = "/register";
    private static final String URL_GET_ALL_USERS_EXCEPT_ME = "/users/ntomikj@endava.com/opponents";

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GameRepository gameRepository;

    private MockMvc mockMvc;
    private UsersDto userTest1;
    private UsersDto userTest2;
    private UsersDto userTest3;
    private Users user1;
    private Users user2;
    private Users user3;
    private Game game1;
    private Game game2;
    private Game game3;
    private Game game4;
    private State state;
    private Gson gson;

    @Before
    public void setUp() {

        gson = new Gson();
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        state = new State("Blablablalba");

        UsersDto userBeforeTest1 = new UsersDto("ntomikj@endava.com", "Pass123!", "ntomikj", "Pass123!");
        UsersDto userBeforeTest2 = new UsersDto("siloski@endava.com", "Pass123!", "siloski", "Pass123!");
        UsersDto userBeforeTest3 = new UsersDto("elashkoska@endava.com", "Pass123!", "elashkoska", "Pass123!");

        userRepository.save(Users.fromUsersDto(userBeforeTest1));
        userRepository.save(Users.fromUsersDto(userBeforeTest2));
        userRepository.save(Users.fromUsersDto(userBeforeTest3));

        user1 = new Users("tomikj@endava.com", "tomikj", "pass123", true);
        user2 = new Users("nastevski@endava.com", "nastevski", "111111", false);
        user3 = new Users("gjorgjiev@endava.com", "gjorgjiev", "654321", true);

        game1 = new Game(user1, user2, Status.ACTIVE, new Date(), Color.BLACK, new State(state));
        game2 = new Game(user1, user3, Status.PENDING, new Date(), Color.WHITE, new State(state));
        game3 = new Game(user2, user3, Status.FINISHED, new Date(), Color.WHITE, new State(state));
        game4 = new Game(user2, user3, Status.FINISHED, new Date(), Color.WHITE, new State(state));


      /*  userTest1 = new Users("vsrbinovski@endava.com", "vsrbinovski", "Pass123!", true);
        userTest2 = new Users("knastevski@endava.com", "knastevski", "P111111!", true);
        userTest3 = new Users("sgjorgjiev@endava.com", "sgjorgjiev", "P654321!", truee);*/
    }

    @Test
    public void registerUser() throws Exception {

        userTest1 = new UsersDto("vsrbinovski@endava.com", "Pass123!", "vsrbinovski", "Pass123!");
        userTest2 = new UsersDto("knastevski@endava.com", "1234567", "knastevski", "1234567");
        userTest3 = new UsersDto("sgjorgjiev.com", "1111111", "sgjorgiev", "1111111");

        String jsonUserString1 = gson.toJson(userTest1);
        String jsonUserString2 = gson.toJson(userTest2);
        String jsonUserString3 = gson.toJson(userTest3);


        mockMvc.perform(post(URL_USER_REGISTRATION)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonUserString1)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());

        mockMvc.perform(post(URL_USER_REGISTRATION)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonUserString2)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());

        mockMvc.perform(post(URL_USER_REGISTRATION)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonUserString3)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());

/*      Optional<Users> userFromDb = userRepository.findByUsername("vsrbinovski");
        UsersDto userFromDbToDto = UsersDto.fromUsers(userFromDb);

        assertThat(userTest1.getUsername()).isEqualTo(userFromDb.get().getUsername());*/

    }

    @Test
    public void login() {


    }

    @Test
    public void findAllUsersExceptMe() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get(URL_GET_ALL_USERS_EXCEPT_ME)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();

        String content = mvcResult.getResponse().getContentAsString();

        Type founderListType = new TypeToken<ArrayList<ActiveUserDto>>(){}.getType();

        List<ActiveUserDto> returnedActiveUsersDto = gson.fromJson(content, founderListType);

        assertThat(returnedActiveUsersDto.size()).isEqualTo(2);

    }

        @Test
    public void getAllGamesForUser() throws Exception {

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

        game1.setInviter(userRepository.findByUsername(user1.getUsername()).get().getId());
        gameRepository.save(game1);
        game2.setInviter(userRepository.findByUsername(user1.getUsername()).get().getId());
        gameRepository.save(game2);
        game3.setInviter(userRepository.findByUsername(user2.getUsername()).get().getId());
        gameRepository.save(game3);

        mockMvc.perform(get("/users/{id}/games", user2.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

    }
}
