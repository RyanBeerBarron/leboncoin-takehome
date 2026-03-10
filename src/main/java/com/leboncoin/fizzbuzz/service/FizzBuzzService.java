package com.leboncoin.fizzbuzz.service;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.springframework.stereotype.Service;

@Service
public class FizzBuzzService {

    public record Predicate(int divisor, String value) {}

    public record Query(int limit, List<Predicate> predicates) {}

    public Stream<String> generate(Query query) {
        return IntStream.rangeClosed(1, query.limit).mapToObj(n -> computeValue(n, query.predicates));
    }

    private String computeValue(int n, List<Predicate> predicates) {
        var result = new StringBuilder();

        for (var replacement : predicates) {
            if (n % replacement.divisor() == 0) {
                result.append(replacement.value());
            }
        }

        return result.isEmpty() ? String.valueOf(n) : result.toString();
    }
}
