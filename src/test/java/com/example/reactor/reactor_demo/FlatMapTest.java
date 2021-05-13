package com.example.reactor.reactor_demo;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.time.Duration;

class FlatMapTest {

    @Test
    void flatMap() {
        Flux<Integer> data = Flux.just(Tuples.of(1, 300L), Tuples.of(2, 200L), Tuples.of(3, 100L)).flatMap(this::delayReplyFor);
        StepVerifier.create(data).expectNext(3, 2, 1).verifyComplete();

        Flux<Integer> data2 = Flux.just(Tuples.of(1, 300L), Tuples.of(2, 200L), Tuples.of(3, 100L)).flatMap(this::delayReplyForFlux);
        StepVerifier.create(data2).expectNext(30, 20, 31, 10, 21, 11).verifyComplete();
    }

    private Flux<Integer> delayReplyForFlux(Tuple2<Integer, Long> tuple2) {
        var n = tuple2.getT1() * 10;
        var delay = tuple2.getT2();
        return Flux.just(n, n + 1).delayElements(Duration.ofMillis(delay));
    }

    private Mono<Integer> delayReplyFor(Tuple2<Integer, Long> tuple2) {
        var n = tuple2.getT1();
        var delay = tuple2.getT2();
        return Mono.just(n).delayElement(Duration.ofMillis(delay));
    }
}
