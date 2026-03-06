package com.leboncoin.fizzbuzz.service;

import com.leboncoin.fizzbuzz.dto.FizzBuzzRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FizzBuzzServiceTest {

    private FizzBuzzService fizzBuzzService;

    @BeforeEach
    void setUp() {
        fizzBuzzService = new FizzBuzzService();
    }

    @Test
    void generate_classicFizzBuzz_returnsCorrectSequence() {
        var request = new FizzBuzzRequest(3, 5, 15, "fizz", "buzz");

        List<String> result = fizzBuzzService.generate(request).toList();

        var expected = List.of(
                "1", "2", "fizz", "4", "buzz",
                "fizz", "7", "8", "fizz", "buzz",
                "11", "fizz", "13", "14", "fizzbuzz"
        );
        assertEquals(expected, result);
    }

    @Test
    void generate_customStrings_usesProvidedStrings() {
        var request = new FizzBuzzRequest(2, 3, 6, "foo", "bar");

        List<String> result = fizzBuzzService.generate(request).toList();

        assertEquals(List.of("1", "foo", "bar", "foo", "5", "foobar"), result);
    }

    @Test
    void generate_limitOfOne_returnsSingleElement() {
        var request = new FizzBuzzRequest(3, 5, 1, "fizz", "buzz");

        List<String> result = fizzBuzzService.generate(request).toList();

        assertEquals(List.of("1"), result);
    }

    @Test
    void generate_sameDivisors_concatenatesBothStrings() {
        var request = new FizzBuzzRequest(2, 2, 4, "foo", "bar");

        List<String> result = fizzBuzzService.generate(request).toList();

        assertEquals(List.of("1", "foobar", "3", "foobar"), result);
    }

    @Test
    void generate_preservesParameterOrder() {
        var request = new FizzBuzzRequest(5, 3, 15, "buzz", "fizz");

        List<String> result = fizzBuzzService.generate(request).toList();

        // str1 (buzz) comes before str2 (fizz) in output
        var expected = List.of(
                "1", "2", "fizz", "4", "buzz",
                "fizz", "7", "8", "fizz", "buzz",
                "11", "fizz", "13", "14", "buzzfizz"
        );
        assertEquals(expected, result);
    }
}
