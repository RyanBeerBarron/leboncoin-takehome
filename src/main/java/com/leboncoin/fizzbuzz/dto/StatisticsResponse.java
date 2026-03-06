package com.leboncoin.fizzbuzz.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Statistics response containing the most frequent request")
public record StatisticsResponse(
        @Schema(description = "The most frequent request parameters, null if no requests have been made")
        FizzBuzzRequest mostFrequentRequest,

        @Schema(description = "Number of times this request has been made")
        long hits
) {
}
