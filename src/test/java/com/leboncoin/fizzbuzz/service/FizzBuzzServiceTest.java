package com.leboncoin.fizzbuzz.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FizzBuzzServiceTest {

    private FizzBuzzService fizzBuzzService;

    @BeforeEach
    void setUp() {
        fizzBuzzService = new FizzBuzzService();
    }

    @Test
    void generate_classicFizzBuzz_returnsCorrectSequence() {
        var request = new FizzBuzzService.Query(
                15, List.of(new FizzBuzzService.Predicate(3, "fizz"), new FizzBuzzService.Predicate(5, "buzz")));

        List<String> result = fizzBuzzService.generate(request).toList();

        var expected = List.of(
                "1", "2", "fizz", "4", "buzz", "fizz", "7", "8", "fizz", "buzz", "11", "fizz", "13", "14", "fizzbuzz");
        assertEquals(expected, result);
    }

    @Test
    void generate_customStrings_usesProvidedStrings() {
        var request = new FizzBuzzService.Query(
                6, List.of(new FizzBuzzService.Predicate(2, "foo"), new FizzBuzzService.Predicate(3, "bar")));

        List<String> result = fizzBuzzService.generate(request).toList();

        assertEquals(List.of("1", "foo", "bar", "foo", "5", "foobar"), result);
    }

    @Test
    void generate_limitOfOne_returnsSingleElement() {
        var request = new FizzBuzzService.Query(
                1, List.of(new FizzBuzzService.Predicate(3, "fizz"), new FizzBuzzService.Predicate(5, "buzz")));

        List<String> result = fizzBuzzService.generate(request).toList();

        assertEquals(List.of("1"), result);
    }

    @Test
    void generate_sameDivisors_concatenatesBothStrings() {
        var request = new FizzBuzzService.Query(
                4, List.of(new FizzBuzzService.Predicate(2, "fizz"), new FizzBuzzService.Predicate(2, "buzz")));

        List<String> result = fizzBuzzService.generate(request).toList();

        assertEquals(List.of("1", "fizzbuzz", "3", "fizzbuzz"), result);
    }

    @Test
    void generate_preservesParameterOrder() {
        var request = new FizzBuzzService.Query(
                15, List.of(new FizzBuzzService.Predicate(5, "fizz"), new FizzBuzzService.Predicate(3, "buzz")));

        List<String> result = fizzBuzzService.generate(request).toList();

        var expected = List.of(
                "1", "2", "buzz", "4", "fizz", "buzz", "7", "8", "buzz", "fizz", "11", "buzz", "13", "14", "fizzbuzz");
        assertEquals(expected, result);
    }
}
