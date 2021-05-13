package com.example.reactor.reactor_demo;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.test.StepVerifier;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Log4j2
class AsyncApiIntegrationTest {
    private final ExecutorService executorService = Executors.newFixedThreadPool(1);

    @Test
    void async() {
        int count = 5;
        Flux<Integer> integers = Flux.create(emitter -> this.launch(emitter, count));
        StepVerifier
                .create(integers.doFinally(signalType -> this.executorService.shutdown()))
                .expectNextCount(count)
                .verifyComplete();
    }

    private void launch(FluxSink<Integer> integerFluxSink, int count) {
        this.executorService.submit(() -> {
            var integer = new AtomicInteger();
            assertNotNull(integerFluxSink);
            while (integer.get() < count) {
                double random = Math.random();
                integerFluxSink.next(integer.incrementAndGet());
                this.sleep((long) (random * 1_000));
            }
            integerFluxSink.complete();
        });
    }

    private void sleep(long s) {
        try {
            TimeUnit.MILLISECONDS.sleep(s);
        } catch (Exception e) {
            log.error(e);
        }
    }
}
