package com.leboncoin.fizzbuzz.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

@Schema(description = "FizzBuzz request parameters")
public record FizzBuzzRequest(
        @Schema(description = "First divisor", example = "3")
        @Positive(message = "int1 must be positive")
        int int1,

        @Schema(description = "Second divisor", example = "5")
        @Positive(message = "int2 must be positive")
        int int2,

        @Schema(description = "Upper limit (inclusive)", example = "100")
        @Min(value = 1, message = "limit must be at least 1")
        int limit,

        @Schema(description = "String to replace multiples of int1", example = "fizz")
        @NotBlank(message = "str1 must not be blank")
        String str1,

        @Schema(description = "String to replace multiples of int2", example = "buzz")
        @NotBlank(message = "str2 must not be blank")
        String str2
) {
}
