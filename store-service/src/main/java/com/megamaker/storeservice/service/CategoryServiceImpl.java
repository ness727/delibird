package com.megamaker.storeservice.service;

import com.megamaker.storeservice.dto.category.RequestSaveCategory;
import com.megamaker.storeservice.dto.category.ResponseCategory;
import com.megamaker.storeservice.dto.category.ResponseSaveCategory;
import com.megamaker.storeservice.entity.Category;
import com.megamaker.storeservice.mapper.CategoryMapper;
import com.megamaker.storeservice.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public ResponseSaveCategory save(RequestSaveCategory requestSaveCategory) {
        Category savedCategory = categoryRepository.save(CategoryMapper.INSTANCE.toCategory(requestSaveCategory));
        return CategoryMapper.INSTANCE.toResponseSaveCategory(savedCategory);
    }

    @Override
    public List<ResponseCategory> findAll() {
        List<Category> result = categoryRepository.findAll();
        return result.stream().map(CategoryMapper.INSTANCE::toResponseCategory).toList();
    }
}
