# FizzBuzz REST API

A production-ready REST API for generating customizable FizzBuzz sequences with request statistics tracking.

## Dependencies

- **Java 25** - Latest LTS with virtual threads support
- **Spring Boot 4** - Web framework
- **Jackson 3** - JSON serialization with streaming support
- **Gradle 8.12** - Build tool
- **JUnit 6** - Testing framework
- **Springdoc OpenAPI** - Swagger UI for API documentation

## Running the Server

### With Gradle

```bash
./gradlew bootRun
```

### With Docker

```bash
docker build -t fizzbuzz .
docker run -p 8080:8080 fizzbuzz
```

## API Endpoints

### FizzBuzz Generation

```
GET /api/fizzbuzz?int1=3&int2=5&limit=15&str1=fizz&str2=buzz
```

### Statistics

```
GET /api/statistics
```

### Swagger UI

```
http://localhost:8080/swagger
```

## Technical Decisions

### StreamingResponseBody

The FizzBuzz endpoint uses `StreamingResponseBody` with Jackson's `JsonGenerator` to stream the response directly to the client. This approach:

- Avoids loading the entire result into memory
- Allows handling very large `limit` values without OOM errors
- Starts sending data immediately rather than waiting for full computation

### Virtual Threads

Enabled via `spring.threads.virtual.enabled=true`. Each request runs on a lightweight virtual thread, improving throughput for I/O-bound operations without the overhead of platform threads.

### Thread-Safe Statistics

Uses `ConcurrentHashMap` with `LongAdder` for lock-free, high-performance request counting:

- `ConcurrentHashMap` ensures thread-safe map operations
- `LongAdder` provides better performance than `AtomicLong` under high contention

## Potential Extensions

### Centralized Statistics Store

The current in-memory statistics are lost on restart and don't work across multiple instances. Options:

- **Redis** - Fast, supports atomic increments with `INCR`
- **PostgreSQL** - Persistent, supports `ON CONFLICT DO UPDATE`

### Metrics with Micrometer and Prometheus

For production observability:

```java
@Autowired
MeterRegistry registry;

registry.counter("fizzbuzz.requests", "int1", "3", "int2", "5").increment();
```

This enables:
- Time-series data with retention policies
- Grafana dashboards for visualization
- Alerting on anomalies
