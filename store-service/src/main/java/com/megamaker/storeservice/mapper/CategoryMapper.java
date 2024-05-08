package com.megamaker.storeservice.mapper;

import com.megamaker.storeservice.dto.category.RequestSaveCategory;
import com.megamaker.storeservice.dto.category.ResponseCategory;
import com.megamaker.storeservice.dto.category.ResponseSaveCategory;
import com.megamaker.storeservice.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import static org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    ResponseSaveCategory toResponseSaveCategory(Category category);

    Category toCategory(RequestSaveCategory requestSaveCategory);
    ResponseCategory toResponseCategory(Category category);

}
