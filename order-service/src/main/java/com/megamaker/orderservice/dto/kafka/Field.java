package com.megamaker.orderservice.dto.kafka;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Field {
    private String type;
    private boolean optional;
    private String field;
}
