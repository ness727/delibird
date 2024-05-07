package com.megamaker.storeservice.repository;

import com.megamaker.storeservice.dto.store.StoreSearchCondition;
import com.megamaker.storeservice.entity.QStore;
import com.megamaker.storeservice.entity.Store;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.megamaker.storeservice.entity.QStore.*;

@Repository
@Transactional
public class StoreRepositoryImpl implements StoreRepository {
    private final EntityManager entityManager;
    private final JPAQueryFactory queryFactory;

    public StoreRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public Slice<Store> findAll(StoreSearchCondition searchCond, Pageable pageable) {
        String regionCode = searchCond.getRegionCode();
        Integer categoryId = searchCond.getCategoryId();

        BooleanBuilder builder = new BooleanBuilder();

        // 법정동 코드 검색 조건
        if (StringUtils.hasText(regionCode)) {
            builder.and(store.regionCode.like(regionCode.substring(0, 5) + "%"));
        }

        // 카테고리별 검색 조건
        if (categoryId != null) {
            builder.and(store.category.id.eq(categoryId));
        }

        List<Store> result = queryFactory.select(store)
                .from(store)
                .where(builder)
                .fetch();

        return null;
    }
}
