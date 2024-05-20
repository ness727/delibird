package com.megamaker.cartservice.util;

import com.megamaker.cartservice.domain.Cart;
import com.megamaker.cartservice.dto.RequestAddCart;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import static org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CartMapper {
    CartMapper INSTANCE = Mappers.getMapper(CartMapper.class);

    Cart toCart(RequestAddCart requestAddCart);
}
