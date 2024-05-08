package com.megamaker.storeservice.service;

import com.megamaker.storeservice.dto.category.RequestSaveCategory;
import com.megamaker.storeservice.dto.category.ResponseSaveCategory;
import com.megamaker.storeservice.dto.store.RequestSaveStore;
import com.megamaker.storeservice.dto.store.ResponseSaveStore;
import com.megamaker.storeservice.entity.Category;
import com.megamaker.storeservice.entity.Store;
import com.megamaker.storeservice.repository.CategoryRepository;
import com.megamaker.storeservice.repository.StoreRepository;
import com.megamaker.storeservice.repository.StoreRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.*;


@DataJpaTest
@Import(StoreRepositoryImpl.class)
class StoreServiceImplTest {
    private final StoreService storeService;
    private final CategoryService categoryService;
    private final StoreRepository storeRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public StoreServiceImplTest(StoreRepository storeRepository, CategoryRepository categoryRepository) {
        this.storeRepository = storeRepository;
        this.categoryRepository = categoryRepository;
        this.storeService = new StoreServiceImpl(storeRepository, categoryRepository);
        this.categoryService = new CategoryServiceImpl(categoryRepository);
    }

    @Test
    public void 매장_저장에_성공한다() {
        // given
        RequestSaveCategory requestSaveCategory = RequestSaveCategory.builder()
                .name("중식")
                .build();
        ResponseSaveCategory savedCategory = categoryService.save(requestSaveCategory);
        RequestSaveStore requestSaveStore = RequestSaveStore.builder()
                .categoryId(savedCategory.getId())
                .name("매장1")
                .build();

        // when
        ResponseSaveStore savedStore = storeService.save(requestSaveStore);

        // then
        assertThat(savedStore.getName()).isEqualTo(requestSaveStore.getName());
    }
}