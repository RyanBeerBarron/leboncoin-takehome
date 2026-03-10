package com.leboncoin.fizzbuzz.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/// Putting the whole description in a separate file to keep {@link com.leboncoin.fizzbuzz.controller.FizzBuzzController
// FizzBuzzController} easier to read
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(
        summary = "Generate FizzBuzz sequence",
        description = "Returns a streamed JSON array of FizzBuzz values",
        responses = {
            @ApiResponse(
                    description = "Simple response body",
                    responseCode = "200",
                    content = {
                        @Content(
                                mediaType = "application/json",
                                examples = {@ExampleObject(value = "[\"1\", \"2\", \"fizz\", \"4\", \"buzz\"]")},
                                array = @ArraySchema(schema = @Schema(implementation = String.class)))
                    })
        })
public @interface FizzBuzzSwaggerDescription {}
