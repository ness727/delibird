package com.megamaker.storeservice.repository;

import com.megamaker.storeservice.dto.store.StoreSearchCondition;
import com.megamaker.storeservice.entity.QProduct;
import com.megamaker.storeservice.entity.QStore;
import com.megamaker.storeservice.entity.Store;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.SimplePath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static com.megamaker.storeservice.entity.QStore.store;
import static org.springframework.util.ObjectUtils.isEmpty;

@Slf4j
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
    public Store save(Store store) {
        entityManager.persist(store);
        return store;
    }

    @Override
    public Store find(Long storeId) {
        return queryFactory.select(store)
                .from(store)
                .leftJoin(store.productList, QProduct.product).on()
                .fetchJoin()
                .where(store.id.eq(storeId))
                .distinct()
                .fetchOne();
    }

    @Override
    public Slice<Store> findAll(StoreSearchCondition searchCond, Pageable pageable) {
        // 검색 조건 설정
        BooleanBuilder builder = getCondResult(searchCond);

        List<Store> result = queryFactory.select(store)
                .from(store)
                .where(builder)
                .orderBy(getOrderSpecifiers(pageable))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 슬라이스로 변환
        return toSlice(result, pageable);
    }

    private static BooleanBuilder getCondResult(StoreSearchCondition searchCondition) {
        log.info(searchCondition.toString());
        BooleanBuilder builder = new BooleanBuilder();
        String regionCode = searchCondition.getRegionCode();
        Integer categoryId = searchCondition.getCategoryId();
        String storeName = searchCondition.getStoreName();

        // 법정동 코드 검색 조건
        if (StringUtils.hasText(regionCode)) {
            builder.and(store.regionCode.like(regionCode.substring(0, 5) + "%"));
        }

        // 카테고리별 검색 조건
        if (categoryId != null) {
            builder.and(store.category.id.eq(categoryId));
        }

        // 매장명 검색 조건
        if (StringUtils.hasText(storeName)) {
            builder.and(store.name.like("%" + storeName + "%"));
        }

        return builder;
    }

    private static Slice<Store> toSlice(List<Store> result, Pageable pageable) {
        int pageSize = pageable.getPageSize();
        boolean hasNext = false;

        // 다음 슬라이스 있는지 확인
        if (result.size() > pageSize) {
            hasNext = true;
            result.remove(pageSize);
        }
        return new SliceImpl<>(result, pageable, hasNext);
    }

    // 정렬 조건 획득
    private static OrderSpecifier[] getOrderSpecifiers(Pageable pageable) {
        List<OrderSpecifier> orderList = new ArrayList<>();

        if (!isEmpty(pageable.getSort())) {
            for (Sort.Order order : pageable.getSort()) {
                Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;

                switch (order.getProperty()) {
                    case "name":
                        orderList.add(new OrderSpecifier<>(direction, store.name));
                        break;
                    case "deliveryFees":
                        orderList.add(new OrderSpecifier<>(direction, store.deliveryFees));
                    default:
                        break;
                }
            }
        }
        return orderList.toArray(OrderSpecifier[]::new);
    }
}
