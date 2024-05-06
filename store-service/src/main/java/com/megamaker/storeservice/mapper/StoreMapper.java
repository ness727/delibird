package com.megamaker.storeservice.mapper;

import com.megamaker.storeservice.dto.store.ResponseStore;
import com.megamaker.storeservice.entity.Store;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import static org.mapstruct.MappingConstants.*;

@Mapper(componentModel = ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StoreMapper {
    StoreMapper INSTANCE = Mappers.getMapper(StoreMapper.class);

    ResponseStore toResponseStore(Store store);

}
