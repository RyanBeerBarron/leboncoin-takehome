package com.leboncoin.fizzbuzz.controller;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leboncoin.fizzbuzz.dto.FizzBuzzRequest;
import com.leboncoin.fizzbuzz.service.FizzBuzzService;
import com.leboncoin.fizzbuzz.service.StatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

@RestController
@RequestMapping("/api/fizzbuzz")
@Tag(name = "FizzBuzz", description = "FizzBuzz generation API")
public class FizzBuzzController {

    private final FizzBuzzService fizzBuzzService;
    private final StatisticsService statisticsService;
    private final ObjectMapper objectMapper;

    public FizzBuzzController(FizzBuzzService fizzBuzzService, StatisticsService statisticsService, ObjectMapper objectMapper) {
        this.fizzBuzzService = fizzBuzzService;
        this.statisticsService = statisticsService;
        this.objectMapper = objectMapper;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Generate FizzBuzz sequence", description = "Returns a streamed JSON array of FizzBuzz values")
    public StreamingResponseBody generate(@Valid FizzBuzzRequest request) {
        statisticsService.recordRequest(request);

        return outputStream -> {
            try (var generator = objectMapper.getFactory().createGenerator(outputStream);
                 var stream = fizzBuzzService.generate(request)) {

                generator.writeStartArray();
                stream.forEach(value -> {
                    try {
                        generator.writeString(value);
                    } catch (Exception e) {
                        throw new RuntimeException("Error writing value to stream", e);
                    }
                });
                generator.writeEndArray();
            }
        };
    }
}
