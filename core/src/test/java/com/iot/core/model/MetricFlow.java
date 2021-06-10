package com.iot.core.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MetricFlow {

    private MetricValue metricValue;

    @BeforeEach
    void init() {
        metricValue = new MetricValue();
        metricValue.setFlow(Arrays.asList("potato", "tomato", "carrot"));
    }

    @Test
    void firstItemFetch() {
        assertEquals("potato", metricValue.firstFlowItem());
    }

    @Test
    void lastItemFetch() {
        assertEquals("carrot", metricValue.lastFlowItem());
    }

    @Test
    void flow() {
        Queue<String> expectedResponses = new LinkedBlockingQueue<String>(Arrays.asList("tomato", "carrot"));
        String current = metricValue.firstFlowItem();
        Optional<String> value = metricValue.nextItem(current);
        while (value.isPresent()) {
            assertEquals(expectedResponses.peek(), value.get(), "Expected response " + expectedResponses.peek());
            expectedResponses.poll();
            current = value.get();
            value = metricValue.nextItem(current);
        }
        assertTrue(expectedResponses.isEmpty(), "Expected all responses to be collected, got:" + expectedResponses);
    }

}
