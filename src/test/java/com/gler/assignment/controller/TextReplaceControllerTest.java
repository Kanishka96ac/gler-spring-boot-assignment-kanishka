package com.gler.assignment.controller;

import com.gler.assignment.exception.InvalidTextException;
import com.gler.assignment.service.TextReplaceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TextReplaceController.class)
class TextReplaceControllerTest {

    // Used to perform HTTP requests against the controller
    @Autowired
    private MockMvc mockMvc;

    // Mocked service dependency
    @MockitoBean
    private TextReplaceService textReplaceService;

    // Valid request should return modified text
    @Test
    void shouldReturnModifiedText() throws Exception {

        when(textReplaceService.replace("elephant"))
                .thenReturn("*lephan$");

        mockMvc.perform(get("/replace")
                        .param("text", "elephant"))
                .andExpect(status().isOk())
                .andExpect(content().string("*lephan$"));
    }

    // Length 2 should return empty response body
    @Test
    void shouldReturnEmptyBodyWhenLengthIsTwo() throws Exception {

        when(textReplaceService.replace("ab"))
                .thenReturn("");

        mockMvc.perform(get("/replace")
                        .param("text", "ab"))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    // Invalid input should return 400 Bad Request
    @Test
    void shouldReturnBadRequestWhenTextIsInvalid() throws Exception {

        when(textReplaceService.replace("a"))
                .thenThrow(new InvalidTextException(
                        "Text length must be at least 2"));

        mockMvc.perform(get("/replace")
                        .param("text", "a"))
                .andExpect(status().isBadRequest());
    }
}