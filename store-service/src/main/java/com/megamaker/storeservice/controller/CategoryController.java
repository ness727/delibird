package com.megamaker.storeservice.controller;

import com.megamaker.storeservice.dto.category.RequestCategory;
import com.megamaker.storeservice.dto.category.ResponseCategory;
import com.megamaker.storeservice.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/category")
@RequiredArgsConstructor
@RestController
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public ResponseCategory save(@RequestBody RequestCategory requestCategory) {
        return categoryService.save(requestCategory);
    }
}
