package com.megamaker.storeservice.mapper;

import com.megamaker.storeservice.dto.store.RequestSaveStore;
import com.megamaker.storeservice.dto.store.ResponseListStore;
import com.megamaker.storeservice.dto.store.ResponseSaveStore;
import com.megamaker.storeservice.dto.store.ResponseStore;
import com.megamaker.storeservice.entity.Store;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {ProductMapper.class})
public interface StoreMapper {
//     StoreMapper INSTANCE = Mappers.getMapper(StoreMapper.class);

    ResponseStore toResponseStore(Store store);

    //@Mapping(source = "category.id", target = "categoryId")
    ResponseListStore toResponseListStore(Store store);

    Store toStore(RequestSaveStore requestSaveStore);

    ResponseSaveStore toResponseSaveStore(Store store);

}
