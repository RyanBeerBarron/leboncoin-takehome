package com.leboncoin.fizzbuzz.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class StatisticsControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getMostFrequentRequest_afterFizzBuzzCalls_returnsCorrectStats() throws Exception {
        // Make some fizzbuzz requests
        for (int i = 0; i < 3; i++) {
            mockMvc.perform(get("/api/fizzbuzz")
                    .param("int1", "3")
                    .param("int2", "5")
                    .param("limit", "10")
                    .param("str1", "fizz")
                    .param("str2", "buzz"));
        }

        mockMvc.perform(get("/api/statistics"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.mostFrequentRequest.int1").value(3))
                .andExpect(jsonPath("$.mostFrequentRequest.int2").value(5))
                .andExpect(jsonPath("$.hits").value(3));
    }
}
