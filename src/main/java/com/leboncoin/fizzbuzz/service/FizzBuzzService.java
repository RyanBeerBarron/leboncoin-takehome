package com.leboncoin.fizzbuzz.service;

import com.leboncoin.fizzbuzz.dto.FizzBuzzRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
public class FizzBuzzService {

    private record Replacement(int divisor, String value) {}

    public Stream<String> generate(FizzBuzzRequest request) {
        var replacements = List.of(
                new Replacement(request.int1(), request.str1()),
                new Replacement(request.int2(), request.str2())
        );

        return IntStream.rangeClosed(1, request.limit())
                .mapToObj(n -> computeValue(n, replacements));
    }

    private String computeValue(int n, List<Replacement> replacements) {
        var result = new StringBuilder();

        for (var replacement : replacements) {
            if (n % replacement.divisor() == 0) {
                result.append(replacement.value());
            }
        }

        return result.isEmpty() ? String.valueOf(n) : result.toString();
    }
}
