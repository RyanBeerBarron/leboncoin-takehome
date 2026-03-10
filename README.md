# FizzBuzz REST API

REST API for generating customizable FizzBuzz sequences with request statistics tracking. Using Spring boot 4 and virtual threads.

## Dependencies

- **Java 25** 
- **Spring Boot 4** 
- **Jackson 3** 
- **JUnit 6** 
- **Springdoc OpenAPI** 

## Running the Server

### With Gradle

```bash
$ ./gradlew bootRun
```

To run the tests:
```bash
$ ./gradlew test --rerun
```

### With Docker

```bash
$ docker build -t fizzbuzz .
$ docker run -p 8080:8080 fizzbuzz
```

## API Endpoints
Check the swagger UI
```
http://localhost:8080/swagger
```

## Technical Decisions

### StreamingResponseBody

The FizzBuzz endpoint uses `StreamingResponseBody` with Jackson's `JsonGenerator` to stream the response instead of buffering it in memory. 
With virtual threads, this allows for many concurrent requests that each use low amount of memory, since the entire response does not need to fit in memory.
This provides high scalibity while keeping a simpler programming model as compared to an async non-blocking I/O model.

### Statistics

Uses `ConcurrentHashMap` with `LongAdder` to keep track of the statistics, for simplicity.
But in a real production environment, you would add a telemetry framework for this.
Either (Micrometer)[https://micrometer.io/] **OR** (Opentelemetry)[https://opentelemetry.io/]
And export the metrics to a telemetry backend, like Prometheus or Datadog, etc...<br/>


Example using micrometer:
```java
@Autowired
MeterRegistry registry;

registry.counter("fizzbuzz.requests", "int1", "3", "int2", "5").increment();
```
