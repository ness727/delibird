package com.megamaker.orderservice.dto.kafka;

import com.megamaker.orderservice.dto.kafka.Field;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class Schema {
    private String type;
    private List<Field> fields;
    private boolean optional;
    private String name;
}
