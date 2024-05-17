package com.megamaker.storeservice.controller;

import com.megamaker.storeservice.dto.category.RequestSaveCategory;
import com.megamaker.storeservice.dto.category.ResponseCategory;
import com.megamaker.storeservice.dto.category.ResponseSaveCategory;
import com.megamaker.storeservice.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/categories")
@RequiredArgsConstructor
@RestController
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public ResponseSaveCategory save(@RequestBody RequestSaveCategory requestSaveCategory) {
        return categoryService.save(requestSaveCategory);
    }

    @GetMapping
    public List<ResponseCategory> findAll() {
        return categoryService.findAll();
    }
}
