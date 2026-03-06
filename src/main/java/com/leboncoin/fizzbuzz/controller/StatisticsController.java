package com.leboncoin.fizzbuzz.controller;

import com.leboncoin.fizzbuzz.dto.StatisticsResponse;
import com.leboncoin.fizzbuzz.service.StatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/statistics")
@Tag(name = "Statistics", description = "Request statistics API")
public class StatisticsController {

    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping
    @Operation(summary = "Get most frequent request", description = "Returns the most frequently used FizzBuzz request parameters and hit count")
    public StatisticsResponse getMostFrequentRequest() {
        return statisticsService.getMostFrequentRequest();
    }
}
