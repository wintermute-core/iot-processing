package com.iot.transform;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

import com.iot.core.model.MetricValue;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;

public class KelvinTransformTest {

    @Mock
    private KafkaTemplate<String, MetricValue> kafkaTemplate;

    @InjectMocks
    private KelvinTransformListener kelvinTransformListener;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
        kelvinTransformListener.setListenTopic("potato");
    }

    @Test
    void checkDropEmptyMessages() {
        kelvinTransformListener.listen(MetricValue.builder().build());
        verifyZeroInteractions(kafkaTemplate);
    }

    @Test
    void checkMessageDrop() {
        MetricValue metricValue = MetricValue.builder()
                .flow(Collections.emptyList())
                .build();
        metricValue.setValue(0d);
        kelvinTransformListener.listen(metricValue);

        verifyZeroInteractions(kafkaTemplate);

        Optional<Object> readValue = metricValue.readValue();
        assertTrue(readValue.isPresent());
        assertEquals(-273.15d, readValue.get());
    }

    @Test
    void checkTransform() {
        MetricValue metricValue = MetricValue.builder()
                .flow(Arrays.asList("potato", "tomato"))
                .build();
        metricValue.setValue(0d);
        kelvinTransformListener.listen(metricValue);

        ArgumentCaptor<MetricValue> captor = ArgumentCaptor.forClass(MetricValue.class);
        verify(kafkaTemplate).send(Matchers.eq("tomato"), Matchers.anyString(), captor.capture());

        Optional<Object> readValue = captor.getValue().readValue();
        assertTrue(readValue.isPresent());
        assertEquals(-273.15d, readValue.get());
    }


}
