package com.leboncoin.fizzbuzz.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.leboncoin.fizzbuzz.dto.FizzBuzzRequestDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.json.JsonMapper;

@SpringBootTest
@AutoConfigureMockMvc
class StatisticsControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private final JsonMapper mapper = new JsonMapper();

    @Test
    void getMostFrequentRequest_afterFizzBuzzCalls_returnsCorrectStats() throws Exception {
        var firstBody = new FizzBuzzRequestDTO(3, 5, 10, "fizz", "buzz");
        for (int i = 0; i < 3; i++) {
            mockMvc.perform(post("/api/fizzbuzz")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsBytes(firstBody)));
        }
        var secondBody = new FizzBuzzRequestDTO(3, 5, 10, "toto", "tutu");
        for (int i = 0; i < 4; i++) {
            mockMvc.perform(post("/api/fizzbuzz")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsBytes(secondBody)));
        }
        mockMvc.perform(get("/api/statistics"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.mostFrequentRequest.str1").value("toto"))
                .andExpect(jsonPath("$.mostFrequentRequest.str2").value("tutu"))
                .andExpect(jsonPath("$.hits").value(4));

        for (int i = 0; i < 3; i++) {
            mockMvc.perform(post("/api/fizzbuzz")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsBytes(firstBody)));
        }

        mockMvc.perform(get("/api/statistics"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.mostFrequentRequest.str1").value("fizz"))
                .andExpect(jsonPath("$.mostFrequentRequest.str2").value("buzz"))
                .andExpect(jsonPath("$.hits").value(6));
    }
}
