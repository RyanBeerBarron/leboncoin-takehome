package com.leboncoin.fizzbuzz.service;

import com.leboncoin.fizzbuzz.dto.FizzBuzzRequestDTO;
import com.leboncoin.fizzbuzz.dto.StatisticsResponseDTO;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;
import org.springframework.stereotype.Service;

@Service
public class StatisticsService {

    /// Both `ConcurrentHashMap` and `LongAdder` are thread-safe variants of common data structure.
    /// So why use both ? The `ConcurrentHashMap` is still needed despite the atomic counter for the
    /// `computeIfAbsent` method.
    /// It will create an entry if not present. Which needs to be thread-safe, to prevent one request to override
    /// the newly created `LongAdder` from a concurrent request.
    private final ConcurrentHashMap<FizzBuzzRequestDTO, LongAdder> requestCounts = new ConcurrentHashMap<>();

    public void recordRequest(FizzBuzzRequestDTO request) {
        requestCounts.computeIfAbsent(request, _ -> new LongAdder()).increment();
    }

    public StatisticsResponseDTO getMostFrequentRequest() {
        FizzBuzzRequestDTO mostFrequent = null;
        long maxCount = 0;

        for (Map.Entry<FizzBuzzRequestDTO, LongAdder> entry : requestCounts.entrySet()) {
            long count = entry.getValue().sum();
            if (count > maxCount) {
                maxCount = count;
                mostFrequent = entry.getKey();
            }
        }

        return new StatisticsResponseDTO(mostFrequent, maxCount);
    }
}
