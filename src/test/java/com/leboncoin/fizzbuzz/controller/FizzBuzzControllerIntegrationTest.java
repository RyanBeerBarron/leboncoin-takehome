package com.leboncoin.fizzbuzz.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class FizzBuzzControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void generate_validRequest_returnsJsonArray() throws Exception {
        var expectedJson = """
                ["1","2","fizz","4","buzz","fizz","7","8","fizz","buzz","11","fizz","13","14","fizzbuzz"]
                """;

        mockMvc.perform(get("/api/fizzbuzz")
                        .param("int1", "3")
                        .param("int2", "5")
                        .param("limit", "15")
                        .param("str1", "fizz")
                        .param("str2", "buzz"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));
    }

    @Test
    void generate_invalidParameters_returnsBadRequest() throws Exception {
        mockMvc.perform(get("/api/fizzbuzz")
                        .param("int1", "0")
                        .param("int2", "5")
                        .param("limit", "15")
                        .param("str1", "fizz")
                        .param("str2", "buzz"))
                .andExpect(status().isBadRequest());
    }
}
