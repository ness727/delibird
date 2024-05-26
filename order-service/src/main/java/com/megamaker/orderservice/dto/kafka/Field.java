package com.megamaker.orderservice.dto.kafka;

import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
public class Field {
    private String type;
    private boolean optional;
    private String field;
}
