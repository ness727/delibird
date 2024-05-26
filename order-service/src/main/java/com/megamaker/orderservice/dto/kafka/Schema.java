package com.megamaker.orderservice.dto.kafka;

import lombok.Builder;

import java.sql.Timestamp;
import java.util.List;

@Builder
public class Schema {
    private String type;
    private List<Field> fieldList;
    private boolean optional;
    private String name;
}
