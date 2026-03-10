package com.leboncoin.fizzbuzz.service;

import com.leboncoin.fizzbuzz.dto.FizzBuzzRequestDTO;
import com.leboncoin.fizzbuzz.dto.StatisticsResponseDTO;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;
import org.springframework.stereotype.Service;

@Service
public class StatisticsService {

    // ConcurrentHashMap is required even though LongAdder is thread-safe because:
    // 1. Map structural modifications (adding new keys) must be thread-safe
    // 2. computeIfAbsent must be atomic to ensure only one LongAdder is created per key
    // LongAdder is used instead of AtomicLong for better performance under high contention.
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
