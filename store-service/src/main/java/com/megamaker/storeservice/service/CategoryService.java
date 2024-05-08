package com.megamaker.storeservice.service;

import com.megamaker.storeservice.dto.category.RequestSaveCategory;
import com.megamaker.storeservice.dto.category.ResponseCategory;
import com.megamaker.storeservice.dto.category.ResponseSaveCategory;

import java.util.List;

public interface CategoryService {
    ResponseSaveCategory save(RequestSaveCategory requestSaveCategory);

    List<ResponseCategory> findAll();
}
