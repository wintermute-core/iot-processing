package com.iot.access.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AggregationOption<K, V> {

    private final K function;

    private final V interval;

}
