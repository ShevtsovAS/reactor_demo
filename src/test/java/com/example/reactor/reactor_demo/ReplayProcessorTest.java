package com.example.reactor.reactor_demo;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.ReplayProcessor;
import reactor.test.StepVerifier;

class ReplayProcessorTest {

    @Test
    void replayProcessor() {
        int historySize = 2;
        boolean unbounded = false;
        ReplayProcessor<String> processor = ReplayProcessor.create(historySize, unbounded);
        produce(processor.sink());
        consume(processor);
    }

    private void produce(FluxSink<String> sink) {
        sink.next("1");
        sink.next("2");
        sink.next("3");
        sink.complete();
    }

    private void consume(Flux<String> publisher) {
        for (int i = 0; i < 5; i++)
            StepVerifier
                    .create(publisher)
                    .expectNext("2")
                    .expectNext("3")
                    .verifyComplete();
    }
}
