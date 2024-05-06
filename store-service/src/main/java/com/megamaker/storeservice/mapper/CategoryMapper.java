package com.megamaker.storeservice.mapper;

import com.megamaker.storeservice.dto.category.RequestCategory;
import com.megamaker.storeservice.dto.category.ResponseCategory;
import com.megamaker.storeservice.dto.store.ResponseStore;
import com.megamaker.storeservice.entity.Category;
import com.megamaker.storeservice.entity.Store;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import static org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    ResponseCategory toResponseCategory(Category category);

    Category toCategory(RequestCategory requestCategory);

}
