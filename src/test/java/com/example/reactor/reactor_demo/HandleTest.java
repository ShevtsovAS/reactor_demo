package com.example.reactor.reactor_demo;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

class HandleTest {

    @Test
    void handle() {

        StepVerifier
                .create(handle(5, 4))
                .expectNext(0, 1, 2, 3)
                .expectError(IllegalArgumentException.class)
                .verify();

        StepVerifier
                .create(this.handle(3, 3))
                .expectNext(0, 1, 2)//
                .verifyComplete();

    }

    private Flux<Integer> handle(int max, int numberToError) {
        return Flux
                .range(0, max)
                .handle((value, sink) -> {
                    var upTo = Stream.iterate(0, i -> i < numberToError, i -> i + 1).collect(toList());
                    if (upTo.contains(value)) {
                        sink.next(value);
                        return;
                    }
                    if (value == numberToError) {
                        sink.error(new IllegalArgumentException("No 4 for you!"));
                        return;
                    }
                    sink.complete();
                });
    }
}
