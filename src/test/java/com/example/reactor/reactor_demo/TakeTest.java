package com.example.reactor.reactor_demo;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

class TakeTest {

    @Test
    void take() {
        var count = 10;
        Flux<Integer> take = range().take(count);
        StepVerifier.create(take).expectNextCount(count).verifyComplete();
    }

    @Test
    void takeUntil() {
        //noinspection WrapperTypeMayBePrimitive
        Integer count = 49;
        Flux<Integer> take = range().takeUntil(count::equals);
        StepVerifier.create(take).expectNextCount(count + 1).verifyComplete();
    }

    private Flux<Integer> range() {
        return Flux.range(0, 1000);
    }

}
