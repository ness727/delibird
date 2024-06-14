package com.megamaker.storeservice.mapper;

import com.megamaker.storeservice.dto.product.ResponseProduct;
import com.megamaker.storeservice.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.util.StringUtils;

import static org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {
//    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(source = "image", target = "image", qualifiedByName = "mapImageUrl")
    ResponseProduct toResponseProduct(Product product);

    @Named("mapImageUrl")
    default String mapImageUrl(String image) {
        if (StringUtils.hasText(image)) return "/images/product/" + image;
        else return null;
    }
}
