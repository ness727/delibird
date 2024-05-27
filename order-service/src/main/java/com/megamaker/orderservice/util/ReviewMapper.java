package com.megamaker.orderservice.util;

import com.megamaker.orderservice.dto.OrderDto;
import com.megamaker.orderservice.dto.RequestOrder;
import com.megamaker.orderservice.dto.ResponseOrder;
import com.megamaker.orderservice.dto.ResponseOrderProduct;
import com.megamaker.orderservice.dto.review.ResponseReview;
import com.megamaker.orderservice.entity.Order;
import com.megamaker.orderservice.entity.OrderProduct;
import com.megamaker.orderservice.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import static org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReviewMapper {
    ReviewMapper INSTANCE = Mappers.getMapper(ReviewMapper.class);

    ResponseReview toResponseReview(Review review);
}
