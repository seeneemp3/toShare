package org.personal.booking.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.personal.booking.BookingController;
import org.personal.booking.dto.BookingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.personal.booking.BookingService;
import org.personal.booking.dto.BookingDtoInput;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BookingController.class)
class BookingControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private BookingService bookingService;

    private static final String USER_ID_HEADER = "X-Sharer-User-Id";

    @Test
    void testCreateBooking() throws Exception {
        BookingDtoInput input = new BookingDtoInput();
        BookingDto expected = new BookingDto();
        when(bookingService.create(any(), anyLong())).thenReturn(expected);

        mockMvc.perform(post("/bookings")
                        .header(USER_ID_HEADER, 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    }

    @Test
    void testGetBooking() throws Exception {
        BookingDto expected = new BookingDto();
        when(bookingService.getBookingById(anyLong(), anyLong())).thenReturn(expected);

        mockMvc.perform(get("/bookings/1")
                        .header(USER_ID_HEADER, 1L))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    }

    @Test
    void testGetAllBookingByUser() throws Exception {
        List<BookingDto> expected = List.of(new BookingDto());
        when(bookingService.getAllBookingByUser(anyLong(), anyString())).thenReturn(expected);

        mockMvc.perform(get("/bookings")
                        .header(USER_ID_HEADER, 1L)
                        .param("state", "ALL"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    }

    @Test
    void testGetAllBookingByOwner() throws Exception {
        List<BookingDto> expected = List.of(new BookingDto());
        when(bookingService.getAllBookingByOwner(anyLong(), anyString())).thenReturn(expected);

        mockMvc.perform(get("/bookings/owner")
                        .header(USER_ID_HEADER, 1L)
                        .param("state", "ALL"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    }

    @Test
    void testUpdateBookingApprove() throws Exception {
        BookingDto expected = new BookingDto();
        when(bookingService.update(anyLong(), anyLong(), anyBoolean())).thenReturn(expected);

        mockMvc.perform(patch("/bookings/1")
                        .header(USER_ID_HEADER, 1L)
                        .param("approved", "true"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    }

}
