package com.example.reactor.reactor_demo;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.test.StepVerifier;

class EmitterProcessorTest {

    @Test
    void emitterProcessor() {
        var processor = EmitterProcessor.create(this::produce);
        consume(processor);
    }

    private void produce(FluxSink<Object> sink) {
        sink.next("1");
        sink.next("2");
        sink.next("3");
        sink.complete();
    }

    private void consume(Flux<Object> publisher) {
        StepVerifier //
                .create(publisher)//
                .expectNext("1")//
                .expectNext("2")//
                .expectNext("3")//
                .verifyComplete();
    }
}
