package org.personal.item.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.personal.Item.Item;
import org.personal.Item.ItemController;
import org.personal.Item.ItemService;
import org.personal.Item.comment.CommentDto;
import org.personal.Item.dto.ItemDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ItemController.class)
public class ItemControllerTest {

    private final Long userId = 1L;
    private final String headerName = "X-Sharer-User-Id";
    private final MediaType contentType = MediaType.APPLICATION_JSON;
    @Autowired
    private ObjectMapper jsonMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ItemService itemService;
    private ItemDto itemDto;
    private CommentDto commentDto;

    @BeforeEach
    public void setup() {
        Item item = Item.builder()
                .id(1L)
                .name("itemName")
                .description("itemDescription")
                .available(true)
                .build();

        itemDto = itemDto = ItemDto.builder()
                .id(1L)
                .name("itemName")
                .description("itemDescription")
                .comments(new ArrayList<>())
                .available(true)
                .build();

        commentDto = new CommentDto(1L, "commentText", item, "commentAuthorName", LocalDateTime.now());
    }

    @Test
    public void getAllItems() throws Exception {
        when(itemService.getAll(anyLong())).thenReturn(List.of(itemDto));

        mockMvc.perform(get("/items")
                        .header(headerName, userId)
                        .contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("itemName")))
                .andExpect(jsonPath("$[0].description", is("itemDescription")))
                .andExpect(jsonPath("$[0].available", is(true)))
                .andExpect(jsonPath("$[0].comments", is(Collections.emptyList())))
                .andExpect(content().json(jsonMapper.writeValueAsString(List.of(itemDto))));
    }

    @Test
    public void getItemById() throws Exception {
        when(itemService.getById(anyLong())).thenReturn(itemDto);

        mockMvc.perform(get("/items/1")
                        .header(headerName, userId)
                        .contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("itemName")))
                .andExpect(jsonPath("$.description", is("itemDescription")))
                .andExpect(jsonPath("$.comments", is(Collections.emptyList())))
                .andExpect(jsonPath("$.available", is(true)))
                .andExpect(content().json(jsonMapper.writeValueAsString(itemDto)));
    }

    @Test
    public void searchItems() throws Exception {
        when(itemService.search(any())).thenReturn(List.of(itemDto));

        mockMvc.perform(get("/items/search?text=keyword")
                        .header(headerName, userId)
                        .contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonMapper.writeValueAsString(List.of(itemDto))));
    }

    @Test
    public void addItem() throws Exception {
        when(itemService.add(anyLong(), any())).thenReturn(itemDto);

        mockMvc.perform(post("/items")
                        .header(headerName, userId)
                        .contentType(contentType)
                        .content(jsonMapper.writeValueAsString(itemDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("itemName")))
                .andExpect(jsonPath("$.description", is("itemDescription")))
                .andExpect(jsonPath("$.available", is(true)))
                .andExpect(content().json(jsonMapper.writeValueAsString(itemDto)));
    }

    @Test
    public void updateItem() throws Exception {
        when(itemService.update(anyLong(), anyLong(), any())).thenReturn(itemDto);

        mockMvc.perform(patch("/items/1")
                        .header(headerName, userId)
                        .contentType(contentType)
                        .content(jsonMapper.writeValueAsString(itemDto)))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonMapper.writeValueAsString(itemDto)));
    }

    @Test
    public void deleteItem() throws Exception {
        Mockito.doNothing().when(itemService).delete(anyLong(), anyLong());

        mockMvc.perform(delete("/items/1")
                        .header(headerName, userId)
                        .contentType(contentType))
                .andExpect(status().isOk());
    }

    @Test
    public void createComment() throws Exception {
        when(itemService.createComment(anyLong(), anyLong(), any())).thenReturn(commentDto);

        mockMvc.perform(post("/items/1/comment")
                        .header(headerName, userId)
                        .contentType(contentType)
                        .content(jsonMapper.writeValueAsString(commentDto)))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonMapper.writeValueAsString(commentDto)));
    }

    @Test
    public void getCommentsByItemId() throws Exception {
        when(itemService.getCommentsByItemId(anyLong())).thenReturn(List.of(commentDto));

        mockMvc.perform(get("/items/1/comment")
                        .header(headerName, userId)
                        .contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonMapper.writeValueAsString(List.of(commentDto))));
    }
}
