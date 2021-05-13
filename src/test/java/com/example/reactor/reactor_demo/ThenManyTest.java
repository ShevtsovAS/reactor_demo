package com.example.reactor.reactor_demo;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ThenManyTest {

    @Test
    void thenMany() {
        var letters = new AtomicInteger();
        var numbers = new AtomicInteger();
        var lettersPublisher = Flux.just("a", "b", "c").doOnNext(value -> letters.incrementAndGet());
        var numbersPublisher = Flux.just(1, 2, 3).doOnNext(number -> numbers.incrementAndGet());
        var thisBeforeThat = lettersPublisher.thenMany(numbersPublisher);
        StepVerifier.create(thisBeforeThat).expectNext(1, 2, 3).verifyComplete();
        assertEquals(3, letters.get());
        assertEquals(3, numbers.get());
    }

}
