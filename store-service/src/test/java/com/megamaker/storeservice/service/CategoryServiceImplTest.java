package com.megamaker.storeservice.service;

import com.megamaker.storeservice.dto.category.RequestSaveCategory;
import com.megamaker.storeservice.dto.category.ResponseSaveCategory;
import com.megamaker.storeservice.repository.CategoryRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


@DataJpaTest
class CategoryServiceImplTest {
    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImplTest(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
        this.categoryService = new CategoryServiceImpl(categoryRepository);
    }

    @Test
    public void 카테고리_저장에_성공한다() {
        // given
        RequestSaveCategory requestSaveCategory = com.megamaker.storeservice.dto.category.RequestSaveCategory.builder()
                .name("중식")
                .image("/sfs/hfg")
                .build();

        // when
        ResponseSaveCategory savedCategory = categoryService.save(requestSaveCategory);

        // then
        Assertions.assertThat(requestSaveCategory.getName()).isEqualTo(savedCategory.getName());
    }
}