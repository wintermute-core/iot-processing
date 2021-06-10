package com.iot.sinkdb.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

import com.iot.core.model.MetricValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class MessagesHandlingTest {

    @Mock
    private MetricSinkService metricSinkService;

    @InjectMocks
    private QueueListener queueListener;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void checkDropEmptyMessages() {
        queueListener.listen(MetricValue.builder().build());
        verifyZeroInteractions(metricSinkService);
    }

    @Test
    void checkSinkPassing() {
        MetricValue metricValue = MetricValue.builder().build();
        metricValue.setValue("potato");
        queueListener.listen(metricValue);
        verify(metricSinkService).sink(metricValue);
    }

}
