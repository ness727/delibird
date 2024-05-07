package com.megamaker.storeservice.service;

import com.megamaker.storeservice.dto.category.RequestCategory;
import com.megamaker.storeservice.dto.category.ResponseCategory;
import com.megamaker.storeservice.entity.Category;
import com.megamaker.storeservice.mapper.CategoryMapper;
import com.megamaker.storeservice.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public ResponseCategory save(RequestCategory requestCategory) {
        Category savedCategory = categoryRepository.save(CategoryMapper.INSTANCE.toCategory(requestCategory));
        return CategoryMapper.INSTANCE.toResponseCategory(savedCategory);
    }
}
