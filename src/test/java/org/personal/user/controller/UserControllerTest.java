package org.personal.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.personal.user.User;
import org.personal.user.UserController;
import org.personal.user.UserServiceImpl;
import org.personal.user.dto.UserDto;
import org.personal.user.dto.UserMapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {
    @Autowired
    ObjectMapper jsonMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserServiceImpl service;
    @Mock
    private UserMapperImpl mapper;
    private final Charset utf = StandardCharsets.UTF_8;
    private final MediaType json = MediaType.APPLICATION_JSON;
    private User user;
    private UserDto userDto;

    @BeforeEach
    public void setup() {
        userDto = new UserDto(1L, "user@user.com", "user");
        user = User.builder()
                .id(1L)
                .name("user")
                .email("user@user.com")
                .build();

        when(mapper.toDto(user)).thenReturn(userDto);
        when(mapper.fromDto(userDto)).thenReturn(user);
    }

    @Test
    public void getAll() throws Exception {
        List<UserDto> userDtoList = List.of(mapper.toDto(user));
        when(service.getAllUsers()).thenReturn(userDtoList);

        mockMvc.perform(get("/users")
                        .characterEncoding(utf)
                        .contentType(json)
                        .header("X-Sharer-User-Id", 1L)
                        .accept(json))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonMapper.writeValueAsString(List.of(mapper.toDto(user)))));
    }

    @Test
    public void getById() throws Exception {
        when(service.getUserById(1L)).thenReturn(userDto);
        mockMvc.perform(get("/users/1")
                        .characterEncoding(utf)
                        .contentType(json)
                        .header("X-Sharer-User-Id", 1L)
                        .accept(json))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonMapper.writeValueAsString(mapper.toDto(user))));
    }

    @Test
    public void getByEmail() throws Exception {
        when(service.getUserByEmail("email@e.com")).thenReturn(userDto);
        mockMvc.perform(get("/users/e/email@e.com")
                        .characterEncoding(utf)
                        .contentType(json)
                        .header("X-Sharer-User-Id", 1L)
                        .accept(json))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonMapper.writeValueAsString(mapper.toDto(user))));
    }

    @Test
    public void add() throws Exception {
        when(service.addUser(any())).thenReturn(userDto);

        mockMvc.perform(post("/users")
                        .content(jsonMapper.writeValueAsString(mapper.toDto(user)))
                        .characterEncoding(utf)
                        .contentType(json)
                        .header("X-Sharer-User-Id", 1L))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonMapper.writeValueAsString(mapper.toDto(user))));
    }

    @Test
    public void update() throws Exception {
        User updatedUser = user;
        updatedUser.setName("update");
        userDto.setName("update");

        when(service.updateUser(anyLong(), any())).thenReturn(userDto);
        mockMvc.perform(patch("/users/{userId}", 1L)
                        .header("X-Sharer-User-Id", 1L)
                        .characterEncoding(utf)
                        .contentType(json)
                        .content(jsonMapper.writeValueAsString(updatedUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("update")))
                .andExpect(jsonPath("$.email", is("user@user.com")));
    }

    @Test
    void deleteUser() throws Exception {
        mockMvc.perform(delete("/users" + "/1"))
                .andExpect(status().isOk());
        verify(service, times(1))
                .deleteUser(anyLong());
    }
}
