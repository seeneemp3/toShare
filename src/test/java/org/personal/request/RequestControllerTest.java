package org.personal.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.personal.Item.Item;
import org.personal.User.User;
import org.personal.User.dto.UserDto;
import org.personal.request.dto.CreatedRequestDto;
import org.personal.request.dto.RequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = RequestController.class)
public class RequestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RequestService requestService;

    @InjectMocks
    private RequestController requestController;
    private RequestDto requestDto;
    private Request request;
    private CreatedRequestDto createdRequestDto;
    private User user;
    private UserDto userDto;

    @BeforeEach
    public void setup() {
        user = User.builder().id(1L).name("user").email("user@user.com").build();
        request = new Request(1L, "req1", user, LocalDateTime.now());
        requestDto = new RequestDto(1L, "req1", user, LocalDateTime.now(), List.of(new Item(1L, user, "item1", "item1", true, request.getId())));
        createdRequestDto = new CreatedRequestDto("req1");
    }
    @Test
    public void testAdd() throws Exception {
        when(requestService.create(anyLong(), any())).thenReturn(requestDto);

        mockMvc.perform(post("/requests")
                        .header("X-Sharer-User-Id", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createdRequestDto)))
                .andExpect(status().isOk());
    }

    @Test
    public void testFindAllById() throws Exception {
        List<RequestDto> requestDtos = Collections.emptyList();
        when(requestService.findAllById(anyLong())).thenReturn(requestDtos);

        mockMvc.perform(get("/requests")
                        .header("X-Sharer-User-Id", 1))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(requestDtos)));
    }

    @Test
    public void testFindById() throws Exception {
        when(requestService.findById(anyLong(), anyLong())).thenReturn(requestDto);

        mockMvc.perform(get("/requests/1")
                        .header("X-Sharer-User-Id", 1))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(requestDto)));
    }

    @Test
    public void testGetAllLimit() throws Exception {
        List<RequestDto> requestDtos = Collections.emptyList();
        when(requestService.getAllLimit(anyLong(), anyInt(), anyInt())).thenReturn(requestDtos);

        mockMvc.perform(get("/requests/all")
                        .header("X-Sharer-User-Id", 1)
                        .param("from", "0")
                        .param("size", "1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(requestDtos)));
    }
}
