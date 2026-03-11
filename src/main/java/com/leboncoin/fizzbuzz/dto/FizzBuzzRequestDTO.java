package com.leboncoin.fizzbuzz.dto;

import com.leboncoin.fizzbuzz.service.FizzBuzzService;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import java.util.List;

@Schema(description = "FizzBuzz request parameters")
public record FizzBuzzRequestDTO(
        @Schema(description = "First divisor", example = "3") @Positive(message = "int1 must be positive")
        int int1,

        @Schema(description = "Second divisor", example = "5") @Positive(message = "int2 must be positive")
        int int2,

        @Schema(description = "Upper limit (inclusive)", example = "100")
        @Min(value = 1, message = "limit must be at least 1")
        int limit,

        @Schema(description = "String to replace multiples of int1", example = "fizz")
        @NotBlank(message = "str1 must not be blank")
        String str1,

        @Schema(description = "String to replace multiples of int2", example = "buzz")
        @NotBlank(message = "str2 must not be blank")
        String str2) {

    /// To avoid the `service` package to call anything in the `controller` package, I added some indirection.<br/>
    /// In the futur, if we want to modularize the project and create a library module without any web
    /// component,
    /// we need to make sure that **NOTHING** in the `service` package knows about the web packages, like
    /// `controller` or `dto`
    /// This principle follows 'Hexagonal Architecture'
    public FizzBuzzService.Query toQuery() {
        return new FizzBuzzService.Query(
                this.limit,
                List.of(
                        new FizzBuzzService.Predicate(this.int1, this.str1),
                        new FizzBuzzService.Predicate(this.int2, this.str2)));
    }
}
