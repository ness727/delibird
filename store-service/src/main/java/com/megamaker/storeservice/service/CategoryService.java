package com.megamaker.storeservice.service;

import com.megamaker.storeservice.dto.category.RequestCategory;
import com.megamaker.storeservice.dto.category.ResponseCategory;

public interface CategoryService {
    ResponseCategory save(RequestCategory requestCategory);
}
