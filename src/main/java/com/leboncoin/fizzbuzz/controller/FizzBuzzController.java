package com.leboncoin.fizzbuzz.controller;

import com.leboncoin.fizzbuzz.dto.FizzBuzzRequestDTO;
import com.leboncoin.fizzbuzz.service.FizzBuzzService;
import com.leboncoin.fizzbuzz.service.StatisticsService;
import com.leboncoin.fizzbuzz.swagger.FizzBuzzSwaggerDescription;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import tools.jackson.core.JsonEncoding;
import tools.jackson.core.ObjectWriteContext;
import tools.jackson.core.json.JsonFactory;

@RestController
@RequestMapping("/api/fizzbuzz")
@Tag(name = "FizzBuzz", description = "FizzBuzz generation API")
public class FizzBuzzController {

    private final FizzBuzzService fizzBuzzService;
    private final StatisticsService statisticsService;
    private final JsonFactory jsonFactory;

    public FizzBuzzController(FizzBuzzService fizzBuzzService, StatisticsService statisticsService) {
        this.fizzBuzzService = fizzBuzzService;
        this.statisticsService = statisticsService;
        this.jsonFactory = new JsonFactory();
    }

    @PostMapping
    @FizzBuzzSwaggerDescription
    public ResponseEntity<StreamingResponseBody> generate(@RequestBody @Valid FizzBuzzRequestDTO request) {
        statisticsService.recordRequest(request);

        StreamingResponseBody body = outputStream -> {
            try (var generator =
                    jsonFactory.createGenerator(ObjectWriteContext.empty(), outputStream, JsonEncoding.UTF8)) {
                var stream = fizzBuzzService.generate(request.toQuery());
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

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(body);
    }
}
