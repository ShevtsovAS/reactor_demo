package com.example.reactor.reactor_demo;


import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

class SimpleFluxFactoriesTest {

    @Test
    void simple() {
        Flux<Integer> rangeOfIntegers = Flux.range(0, 10);
        StepVerifier.create(rangeOfIntegers).expectNextCount(10).verifyComplete();

        Flux<String> letters = Flux.just("A", "B", "C");
        StepVerifier.create(letters).expectNext("A", "B", "C").verifyComplete();

        long now = System.currentTimeMillis();
        Mono<Date> greetingMono = Mono.just(new Date(now));
        StepVerifier.create(greetingMono).expectNext(new Date(now)).verifyComplete();

        Mono<Void> empty = Mono.empty();
        StepVerifier.create(empty).verifyComplete();

        Flux<Integer> fromArray = Flux.fromArray(new Integer[] { 1, 2, 3 });
        StepVerifier.create(fromArray).expectNext(1, 2, 3).verifyComplete();

        Flux<Integer> fromIterable = Flux.fromIterable(Arrays.asList(1, 2, 3));
        StepVerifier.create(fromIterable).expectNext(1, 2, 3).verifyComplete();

        AtomicInteger integer = new AtomicInteger();
        Flux<Integer> integerFlux = Flux.fromStream(Stream.generate(integer::incrementAndGet));
        StepVerifier.create(integerFlux.take(3)).expectNext(1, 2, 3).verifyComplete();
    }
}
