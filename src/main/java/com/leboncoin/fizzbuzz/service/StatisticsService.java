package com.leboncoin.fizzbuzz.service;

import com.leboncoin.fizzbuzz.dto.FizzBuzzRequest;
import com.leboncoin.fizzbuzz.dto.StatisticsResponse;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;

@Service
public class StatisticsService {

    // ConcurrentHashMap is required even though LongAdder is thread-safe because:
    // 1. Map structural modifications (adding new keys) must be thread-safe
    // 2. computeIfAbsent must be atomic to ensure only one LongAdder is created per key
    // LongAdder is used instead of AtomicLong for better performance under high contention.
    private final ConcurrentHashMap<FizzBuzzRequest, LongAdder> requestCounts = new ConcurrentHashMap<>();

    public void recordRequest(FizzBuzzRequest request) {
        requestCounts.computeIfAbsent(request, k -> new LongAdder()).increment();
    }

    public StatisticsResponse getMostFrequentRequest() {
        FizzBuzzRequest mostFrequent = null;
        long maxCount = 0;

        for (Map.Entry<FizzBuzzRequest, LongAdder> entry : requestCounts.entrySet()) {
            long count = entry.getValue().sum();
            if (count > maxCount) {
                maxCount = count;
                mostFrequent = entry.getKey();
            }
        }

        return new StatisticsResponse(mostFrequent, maxCount);
    }
}
