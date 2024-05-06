package com.megamaker.storeservice.service;

import com.megamaker.storeservice.dto.category.RequestCategory;
import com.megamaker.storeservice.dto.category.ResponseCategory;
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
        RequestCategory requestCategory = RequestCategory.builder()
                .name("중식")
                .image("/sfs/hfg")
                .build();

        // when
        ResponseCategory savedCategory = categoryService.save(requestCategory);

        // then
        Assertions.assertThat(requestCategory.getName()).isEqualTo(savedCategory.getName());
    }
}