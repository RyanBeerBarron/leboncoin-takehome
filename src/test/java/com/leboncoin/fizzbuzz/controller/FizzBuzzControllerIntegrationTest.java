package com.leboncoin.fizzbuzz.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.leboncoin.fizzbuzz.dto.FizzBuzzRequestDTO;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import tools.jackson.databind.json.JsonMapper;

@SpringBootTest
@AutoConfigureMockMvc
class FizzBuzzControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private final JsonMapper mapper = new JsonMapper();

    @Test
    void generate_validRequest_returnsJsonArray() throws Exception {
        List<String> expectedResult = List.of(
                "1", "2", "fizz", "4", "buzz", "fizz", "7", "8", "fizz", "buzz", "11", "fizz", "13", "14", "fizzbuzz");

        var body = new FizzBuzzRequestDTO(3, 5, 15, "fizz", "buzz");
        MvcResult mvcResult = mockMvc.perform(post("/api/fizzbuzz")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.mapper.writeValueAsBytes(body)))
                .andExpect(request().asyncStarted())
                .andDo(MvcResult::getAsyncResult)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        Object result = mapper.readValue(mvcResult.getResponse().getContentAsByteArray(), Object.class);
        Assertions.assertEquals(result, expectedResult);
    }

    @Test
    void generate_invalidParameters_returnsBadRequest() throws Exception {
        var body = new FizzBuzzRequestDTO(0, 5, 15, "fizz", "buzz");
        mockMvc.perform(post("/api/fizzbuzz")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.mapper.writeValueAsBytes(body)))
                .andExpect(status().isBadRequest());
    }
}
